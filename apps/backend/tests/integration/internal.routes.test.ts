import { describe, it, expect, beforeAll, afterAll, beforeEach } from 'vitest';
import { createTestApp } from '../helpers/test-app.js';
import { TEST_MERCHANT, TEST_PAYMENT, TEST_SUCCESS_PAYMENT } from '../helpers/fixtures.js';
import type { FastifyInstance } from 'fastify';

describe('Internal Detection Routes', () => {
  let app: FastifyInstance;
  let repos: any;

  beforeAll(async () => {
    const testApp = await createTestApp();
    app = testApp.app;
    repos = testApp.repos;
  });

  afterAll(async () => { await app.close(); });

  beforeEach(() => {
    repos.paymentRepo.findById.mockReset();
    repos.paymentRepo.updateStatus.mockReset();
    repos.eventRepo.create.mockReset();
    repos.merchantRepo.findById.mockReset();
  });

  describe('POST /internal/payments/:id/mark-success', () => {
    it('should mark payment as successful', async () => {
      repos.paymentRepo.findById.mockResolvedValue(TEST_PAYMENT);
      repos.paymentRepo.updateStatus.mockResolvedValue({ ...TEST_PAYMENT, status: 'success' });
      repos.eventRepo.create.mockResolvedValue({});
      repos.merchantRepo.findById.mockResolvedValue({ ...TEST_MERCHANT, webhook_url: null });

      const res = await app.inject({
        method: 'POST',
        url: `/internal/payments/${TEST_PAYMENT.id}/mark-success`,
        headers: { 'x-internal-key': 'test-internal-api-key-1234' },
      });

      expect(res.statusCode).toBe(200);
      expect(res.json().data.status).toBe('success');
    });

    it('should return 401 without internal key', async () => {
      const res = await app.inject({
        method: 'POST',
        url: `/internal/payments/${TEST_PAYMENT.id}/mark-success`,
      });

      expect(res.statusCode).toBe(401);
    });

    it('should return 401 with wrong internal key', async () => {
      const res = await app.inject({
        method: 'POST',
        url: `/internal/payments/${TEST_PAYMENT.id}/mark-success`,
        headers: { 'x-internal-key': 'wrong-key' },
      });

      expect(res.statusCode).toBe(401);
    });

    it('should return 409 for already-succeeded payment', async () => {
      repos.paymentRepo.findById.mockResolvedValue(TEST_SUCCESS_PAYMENT);

      const res = await app.inject({
        method: 'POST',
        url: `/internal/payments/${TEST_SUCCESS_PAYMENT.id}/mark-success`,
        headers: { 'x-internal-key': 'test-internal-api-key-1234' },
      });

      expect(res.statusCode).toBe(409);
    });
  });

  describe('POST /internal/payments/:id/mark-failed', () => {
    it('should mark payment as failed', async () => {
      repos.paymentRepo.findById.mockResolvedValue(TEST_PAYMENT);
      repos.paymentRepo.updateStatus.mockResolvedValue({ ...TEST_PAYMENT, status: 'failed' });
      repos.eventRepo.create.mockResolvedValue({});

      const res = await app.inject({
        method: 'POST',
        url: `/internal/payments/${TEST_PAYMENT.id}/mark-failed`,
        headers: { 'x-internal-key': 'test-internal-api-key-1234' },
        payload: { reason: 'Customer cancelled' },
      });

      expect(res.statusCode).toBe(200);
      expect(res.json().data.status).toBe('failed');
    });
  });
});
