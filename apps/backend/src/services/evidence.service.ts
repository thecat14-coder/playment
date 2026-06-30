import crypto from 'node:crypto';
import type Redis from 'ioredis';
import type { EvidenceUploadInput } from '@gateway/shared';
import type { EvidenceRepository } from '../repositories/evidence.repository.js';
import type { DeviceRepository } from '../repositories/device.repository.js';
import type { NotificationLogRepository } from './notification-log.service.js';
import { generateId } from '../utils/ulid.js';
import { ValidationError, ForbiddenError, ConflictError } from '../utils/errors.js';

export class EvidenceService {
  constructor(
    private evidenceRepo: EvidenceRepository,
    private deviceRepo: DeviceRepository,
    private redis: Redis,
    private notificationLogService?: NotificationLogRepository,
  ) {}

  verifySignature(
    payload: Omit<EvidenceUploadInput, 'signature' | 'nonce'>,
    signature: string,
    deviceSecret: string,
  ): boolean {
    const data = `${payload.amount}|${payload.utr ?? ''}|${payload.notification_timestamp.getTime()}|${payload.raw_notification.slice(0, 100)}`;
    const expected = crypto
      .createHmac('sha256', deviceSecret)
      .update(data)
      .digest('hex');
    if (signature.length !== expected.length) return false;
    try {
      return crypto.timingSafeEqual(Buffer.from(signature), Buffer.from(expected));
    } catch {
      return false;
    }
  }

  async checkNonce(nonce: string): Promise<boolean> {
    const key = `nonce:${nonce}`;
    const exists = await this.redis.exists(key);
    if (exists) return false;
    await this.redis.setex(key, 86400, '1');
    return true;
  }

  async uploadEvidence(
    deviceId: string,
    merchantId: string,
    input: EvidenceUploadInput,
  ) {
    const device = await this.deviceRepo.findById(deviceId);
    if (!device) throw new ForbiddenError('Device not found');
    if (device.status !== 'active') throw new ForbiddenError('Device is not active');
    if (device.merchant_id !== merchantId) throw new ForbiddenError('Device does not belong to this merchant');

    if (!this.verifySignature(input, input.signature, device.device_secret)) {
      throw new ValidationError('Invalid HMAC signature');
    }

    const nonceUnique = await this.checkNonce(input.nonce);
    if (!nonceUnique) {
      throw new ConflictError('Duplicate evidence (nonce already used)');
    }

    const existingNonce = await this.evidenceRepo.findByNonce(input.nonce);
    if (existingNonce) {
      throw new ConflictError('Duplicate evidence (nonce already exists)');
    }

    const evidenceId = generateId();

    const evidence = await this.evidenceRepo.create({
      id: evidenceId,
      device_id: deviceId,
      merchant_id: merchantId,
      raw_notification: input.raw_notification,
      amount: input.amount,
      utr: input.utr ?? null,
      rrn: input.rrn ?? null,
      sender_vpa: input.sender_vpa ?? null,
      sender_name: input.sender_name ?? null,
      upi_app: input.upi_app,
      bank: input.bank ?? null,
      notification_package: input.notification_package,
      notification_timestamp: input.notification_timestamp,
      parser_version: input.parser_version,
      nonce: input.nonce,
      signature: input.signature,
      matched: false,
      matched_payment_id: null,
    });

    if (this.notificationLogService) {
      await this.notificationLogService.logNotification({
        device_id: deviceId,
        package_name: input.notification_package,
        body: input.raw_notification,
        is_payment: true,
        evidence_id: evidenceId,
        received_at: input.notification_timestamp,
      });
    }

    return evidence;
  }

  async getEvidence(id: string) {
    return this.evidenceRepo.findById(id);
  }

  async getEvidenceForPayment(paymentId: string) {
    return this.evidenceRepo.findByPaymentId(paymentId);
  }

  async listEvidence(merchantId: string, filters: { page: number; limit: number; matched?: boolean; from?: Date; to?: Date }) {
    return this.evidenceRepo.listByMerchant(merchantId, filters);
  }
}
