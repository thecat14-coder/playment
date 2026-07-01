import { describe, it, expect, beforeEach, vi } from 'vitest';
import { PaymentService } from '../../src/services/payment.service.js';
import {
  createMockPaymentRepo,
  createMockPaymentEventRepo,
  createMockMerchantRepo,
  createMockUpiService,
  createMockQrService,
  createMockWebhookService,
} from '../helpers/mocks.js';
import { TEST_MERCHANT, TEST_PAYMENT, TEST_SUCCESS_PAYMENT, createPaymentLinkInput } from '../helpers/fixtures.js';
import { getEnv } from '../../src/config/env.js';

describe('PaymentService', () => {
  let service: PaymentService;
  let paymentRepo: ReturnType<typeof createMockPaymentRepo>;
  let eventRepo: ReturnType<typeof createMockPaymentEventRepo>;
  let merchantRepo: ReturnType<typeof createMockMerchantRepo>;
  let upiService: ReturnType<typeof createMockUpiService>;
  let qrService: ReturnType<typeof createMockQrService>;
  let webhookService: ReturnType<typeof createMockWebhookService>;

  beforeEach(() => {
    paymentRepo = createMockPaymentRepo();
    eventRepo = createMockPaymentEventRepo();
    merchantRepo = createMockMerchantRepo();
    upiService = createMockUpiService();
    qrService = createMockQrService();
    webhookService = createMockWebhookService();

    service = new PaymentService(
      paymentRepo as any,
      eventRepo as any,
      merchantRepo as any,
      upiService as any,
      qrService as any,
      webhookService as any,
      getEnv(),
    );
  });

  describe('createPaymentLink', () => {
    it('should create a payment link with all fields', async () => {
      merchantRepo.findById.mockResolvedValue(TEST_MERCHANT);
      paymentRepo.create.mockImplementation(async (data: any) => ({
        ...data,
        created_at: new Date(),
      }));
      eventRepo.create.mockResolvedValue({});

      const input = createPaymentLinkInput();
      const result = await service.createPaymentLink(TEST_MERCHANT.id, input);

      expect(result.amount).toBe(49900);
      expect(result.currency).toBe('INR');
      expect(result.status).toBe('pending');
      expect(result.order_id).toBe('ORD-001');
      expect(result.payment_link).toContain('http://localhost:3002/');
      expect(result.qr_url).toBe('data:image/png;base64,mockqr');
      expect(result.upi_intent).toContain('upi://pay?');

      expect(paymentRepo.create).toHaveBeenCalledOnce();
      expect(eventRepo.create).toHaveBeenCalledOnce();
      expect(upiService.buildIntent).toHaveBeenCalledWith(
        expect.objectContaining({
          upiId: TEST_MERCHANT.upi_id,
          merchantName: TEST_MERCHANT.name,
          amount: 49900,
          note: 'ORD-001',
        }),
      );
      expect(upiService.buildIntent.mock.calls[0][0].transactionRef).toMatch(/^[0-9A-Z]{26}$/);
    });

    it('should throw NotFoundError if merchant does not exist', async () => {
      merchantRepo.findById.mockResolvedValue(null);
      await expect(service.createPaymentLink('nonexistent', createPaymentLinkInput()))
        .rejects.toThrow('Merchant not found');
    });
  });

  describe('markSuccess', () => {
    it('should transition a pending payment to success', async () => {
      paymentRepo.findById.mockResolvedValue(TEST_PAYMENT);
      paymentRepo.updateStatus.mockResolvedValue({ ...TEST_PAYMENT, status: 'success' });
      eventRepo.create.mockResolvedValue({});
      merchantRepo.findById.mockResolvedValue(TEST_MERCHANT);
      webhookService.enqueueDelivery.mockResolvedValue(undefined);

      await service.markSuccess(TEST_PAYMENT.id);

      expect(paymentRepo.updateStatus).toHaveBeenCalledWith(
        TEST_PAYMENT.id,
        'success',
        expect.objectContaining({ paid_at: expect.any(Date) }),
      );
      expect(eventRepo.create).toHaveBeenCalledWith(
        expect.objectContaining({ type: 'payment.success', source: 'detector' }),
      );
      expect(webhookService.enqueueDelivery).toHaveBeenCalledWith(
        expect.objectContaining({
          paymentId: TEST_PAYMENT.id,
          merchantId: TEST_MERCHANT.id,
          webhookUrl: TEST_MERCHANT.webhook_url,
        }),
      );
    });

    it('should throw ConflictError if payment is not pending', async () => {
      paymentRepo.findById.mockResolvedValue(TEST_SUCCESS_PAYMENT);
      await expect(service.markSuccess(TEST_SUCCESS_PAYMENT.id))
        .rejects.toThrow('Cannot mark success payment as success');
    });

    it('should throw NotFoundError if payment does not exist', async () => {
      paymentRepo.findById.mockResolvedValue(null);
      await expect(service.markSuccess('nonexistent'))
        .rejects.toThrow('Payment not found');
    });

    it('should skip webhook if merchant has no webhook URL', async () => {
      paymentRepo.findById.mockResolvedValue(TEST_PAYMENT);
      paymentRepo.updateStatus.mockResolvedValue({ ...TEST_PAYMENT, status: 'success' });
      eventRepo.create.mockResolvedValue({});
      merchantRepo.findById.mockResolvedValue({ ...TEST_MERCHANT, webhook_url: null });

      await service.markSuccess(TEST_PAYMENT.id);
      expect(webhookService.enqueueDelivery).not.toHaveBeenCalled();
    });
  });

  describe('markFailed', () => {
    it('should transition a pending payment to failed', async () => {
      paymentRepo.findById.mockResolvedValue(TEST_PAYMENT);
      paymentRepo.updateStatus.mockResolvedValue({ ...TEST_PAYMENT, status: 'failed' });
      eventRepo.create.mockResolvedValue({});

      await service.markFailed(TEST_PAYMENT.id, 'Timeout');

      expect(paymentRepo.updateStatus).toHaveBeenCalledWith(TEST_PAYMENT.id, 'failed');
      expect(eventRepo.create).toHaveBeenCalledWith(
        expect.objectContaining({ type: 'payment.failed', payload: { reason: 'Timeout' } }),
      );
    });

    it('should throw if payment already succeeded', async () => {
      paymentRepo.findById.mockResolvedValue(TEST_SUCCESS_PAYMENT);
      await expect(service.markFailed(TEST_SUCCESS_PAYMENT.id, 'Late'))
        .rejects.toThrow('Cannot mark success payment as failed');
    });
  });

  describe('markExpired', () => {
    it('should transition a pending payment to expired', async () => {
      paymentRepo.findById.mockResolvedValue(TEST_PAYMENT);
      paymentRepo.updateStatus.mockResolvedValue({ ...TEST_PAYMENT, status: 'expired' });
      eventRepo.create.mockResolvedValue({});

      await service.markExpired(TEST_PAYMENT.id);

      expect(paymentRepo.updateStatus).toHaveBeenCalledWith(TEST_PAYMENT.id, 'expired');
      expect(eventRepo.create).toHaveBeenCalledWith(
        expect.objectContaining({ type: 'payment.expired', source: 'system' }),
      );
    });

    it('should silently skip if payment is no longer pending', async () => {
      paymentRepo.findById.mockResolvedValue(TEST_SUCCESS_PAYMENT);
      await service.markExpired(TEST_SUCCESS_PAYMENT.id);
      expect(paymentRepo.updateStatus).not.toHaveBeenCalled();
    });

    it('should silently skip if payment does not exist', async () => {
      paymentRepo.findById.mockResolvedValue(null);
      await service.markExpired('nonexistent');
      expect(paymentRepo.updateStatus).not.toHaveBeenCalled();
    });
  });

  describe('status listeners', () => {
    it('should notify listeners on status change', async () => {
      const listener = vi.fn();
      service.onStatusChange(listener);

      paymentRepo.findById.mockResolvedValue(TEST_PAYMENT);
      paymentRepo.updateStatus.mockResolvedValue({ ...TEST_PAYMENT, status: 'success' });
      eventRepo.create.mockResolvedValue({});
      merchantRepo.findById.mockResolvedValue({ ...TEST_MERCHANT, webhook_url: null });

      await service.markSuccess(TEST_PAYMENT.id);

      expect(listener).toHaveBeenCalledWith(TEST_PAYMENT.id, 'success');
    });

    it('should allow unsubscribing', async () => {
      const listener = vi.fn();
      const unsub = service.onStatusChange(listener);
      unsub();

      paymentRepo.findById.mockResolvedValue(TEST_PAYMENT);
      paymentRepo.updateStatus.mockResolvedValue({ ...TEST_PAYMENT, status: 'failed' });
      eventRepo.create.mockResolvedValue({});

      await service.markFailed(TEST_PAYMENT.id, 'test');
      expect(listener).not.toHaveBeenCalled();
    });
  });
});
