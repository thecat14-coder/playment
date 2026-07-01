import { PaymentStatus, PaymentEventType, EventSource } from '@gateway/shared';
import type { CreatePaymentLinkInput, WebhookPayload } from '@gateway/shared';
import type { PaymentDetector } from '../detection/detector.interface.js';
import type { PaymentRepository } from '../repositories/payment.repository.js';
import type { PaymentEventRepository } from '../repositories/payment-event.repository.js';
import type { MerchantRepository } from '../repositories/merchant.repository.js';
import type { UpiService } from './upi.service.js';
import type { QrService } from './qr.service.js';
import type { WebhookService } from './webhook.service.js';
import type { Env } from '../config/env.js';
import { generateId } from '../utils/ulid.js';
import { NotFoundError, ConflictError, ValidationError } from '../utils/errors.js';

export class PaymentService implements PaymentDetector {
  private statusListeners = new Set<(paymentId: string, status: string) => void>();

  constructor(
    private paymentRepo: PaymentRepository,
    private eventRepo: PaymentEventRepository,
    private merchantRepo: MerchantRepository,
    private upiService: UpiService,
    private qrService: QrService,
    private webhookService: WebhookService,
    private env: Env,
  ) {}

  async createPaymentLink(merchantId: string, input: CreatePaymentLinkInput) {
    const merchant = await this.merchantRepo.findById(merchantId);
    if (!merchant) throw new NotFoundError('Merchant not found');

    const upiId = merchant.upi_id?.trim() ?? '';
    if (upiId.length < 3 || !upiId.includes('@')) {
      throw new ValidationError(
        'UPI ID is required before creating payment links. Add your UPI ID in account settings.',
        [{ field: 'upi_id', issue: 'UPI ID must be configured' }],
      );
    }

    const paymentId = generateId();

    const upiIntent = this.upiService.buildIntent({
      upiId,
      merchantName: merchant.name,
      amount: input.amount,
      transactionRef: paymentId,
      note: input.order_id,
    });

    console.log(`[upi] intent generated paymentId=${paymentId} uri=${upiIntent}`);

    const qrDataUrl = await this.qrService.generateDataUrl(upiIntent);

    const paymentLink = `${this.env.CHECKOUT_URL}/${paymentId}`;
    const expiresAt = new Date(Date.now() + input.expires_in * 1000);

    const payment = await this.paymentRepo.create({
      id: paymentId,
      merchant_id: merchantId,
      amount: input.amount,
      currency: input.currency,
      status: PaymentStatus.PENDING,
      order_id: input.order_id,
      customer_name: input.customer_name ?? null,
      customer_email: input.customer_email ?? null,
      customer_phone: input.customer_phone ?? null,
      upi_intent: upiIntent,
      qr_url: qrDataUrl,
      payment_link: paymentLink,
      metadata: input.metadata ?? null,
      expires_at: expiresAt,
    });

    await this.eventRepo.create({
      id: generateId(),
      payment_id: paymentId,
      type: PaymentEventType.CREATED,
      payload: { amount: input.amount, order_id: input.order_id },
      source: EventSource.API,
    });

    return {
      id: payment.id,
      payment_link: payment.payment_link,
      qr_url: payment.qr_url,
      upi_intent: payment.upi_intent,
      amount: payment.amount,
      currency: payment.currency,
      status: payment.status,
      order_id: payment.order_id,
      expires_at: payment.expires_at.toISOString(),
      created_at: payment.created_at.toISOString(),
    };
  }

  async getPayment(id: string) {
    const payment = await this.paymentRepo.findById(id);
    if (!payment) throw new NotFoundError('Payment not found');
    return payment;
  }

  async getPaymentForMerchant(merchantId: string, id: string) {
    const payment = await this.paymentRepo.findByMerchantAndId(merchantId, id);
    if (!payment) throw new NotFoundError('Payment not found');
    return payment;
  }

  async listPayments(merchantId: string, filters: Parameters<PaymentRepository['listByMerchant']>[1]) {
    return this.paymentRepo.listByMerchant(merchantId, filters);
  }

  async markSuccess(paymentId: string, metadata?: Record<string, unknown>): Promise<void> {
    const payment = await this.paymentRepo.findById(paymentId);
    if (!payment) throw new NotFoundError('Payment not found');
    if (payment.status !== PaymentStatus.PENDING && payment.status !== PaymentStatus.REVIEW) {
      throw new ConflictError(`Cannot mark ${payment.status} payment as success`);
    }

    const paidAt = new Date();
    await this.paymentRepo.updateStatus(paymentId, PaymentStatus.SUCCESS, {
      paid_at: paidAt,
    });

    await this.eventRepo.create({
      id: generateId(),
      payment_id: paymentId,
      type: PaymentEventType.SUCCESS,
      payload: { metadata: metadata ?? null },
      source: EventSource.DETECTOR,
    });

    this.notifyListeners(paymentId, PaymentStatus.SUCCESS);

    const merchant = await this.merchantRepo.findById(payment.merchant_id);
    if (merchant?.webhook_url) {
      const webhookPayload: WebhookPayload = {
        event: PaymentEventType.SUCCESS,
        payment_id: payment.id,
        order_id: payment.order_id,
        amount: payment.amount,
        currency: payment.currency,
        status: PaymentStatus.SUCCESS,
        paid_at: paidAt.toISOString(),
        confidence: payment.confidence ?? null,
        metadata: payment.metadata as Record<string, unknown> | null,
      };

      await this.webhookService.enqueueDelivery({
        paymentId: payment.id,
        merchantId: merchant.id,
        webhookUrl: merchant.webhook_url,
        webhookSecret: merchant.webhook_secret,
        payload: webhookPayload,
      });
    }
  }

  async markFailed(paymentId: string, reason: string): Promise<void> {
    const payment = await this.paymentRepo.findById(paymentId);
    if (!payment) throw new NotFoundError('Payment not found');
    if (payment.status !== PaymentStatus.PENDING && payment.status !== PaymentStatus.REVIEW) {
      throw new ConflictError(`Cannot mark ${payment.status} payment as failed`);
    }

    await this.paymentRepo.updateStatus(paymentId, PaymentStatus.FAILED);

    await this.eventRepo.create({
      id: generateId(),
      payment_id: paymentId,
      type: PaymentEventType.FAILED,
      payload: { reason },
      source: EventSource.DETECTOR,
    });

    this.notifyListeners(paymentId, PaymentStatus.FAILED);
  }

  async markExpired(paymentId: string): Promise<void> {
    const payment = await this.paymentRepo.findById(paymentId);
    if (!payment || payment.status !== PaymentStatus.PENDING) return;

    await this.paymentRepo.updateStatus(paymentId, PaymentStatus.EXPIRED);

    await this.eventRepo.create({
      id: generateId(),
      payment_id: paymentId,
      type: PaymentEventType.EXPIRED,
      payload: {},
      source: EventSource.SYSTEM,
    });

    this.notifyListeners(paymentId, PaymentStatus.EXPIRED);
  }

  onStatusChange(listener: (paymentId: string, status: string) => void) {
    this.statusListeners.add(listener);
    return () => { this.statusListeners.delete(listener); };
  }

  private notifyListeners(paymentId: string, status: string) {
    for (const listener of this.statusListeners) {
      listener(paymentId, status);
    }
  }
}
