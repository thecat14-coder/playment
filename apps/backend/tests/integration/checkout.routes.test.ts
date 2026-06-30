import { describe, it, expect, beforeAll, afterAll, beforeEach } from 'vitest';
import { createTestApp } from '../helpers/test-app.js';
import { TEST_MERCHANT, TEST_PAYMENT } from '../helpers/fixtures.js';
import type { FastifyInstance } from 'fastify';

describe('Checkout Routes', () => {
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
    repos.merchantRepo.findById.mockReset();
  });

  describe('GET /v1/checkout/:paymentId', () => {
    it('should return checkout data without authentication', async () => {
      repos.paymentRepo.findById.mockResolvedValue(TEST_PAYMENT);
      repos.merchantRepo.findById.mockResolvedValue(TEST_MERCHANT);

      const res = await app.inject({
        method: 'GET',
        url: `/v1/checkout/${TEST_PAYMENT.id}`,
      });

      expect(res.statusCode).toBe(200);
      const data = res.json().data;
      expect(data.payment_id).toBe(TEST_PAYMENT.id);
      expect(data.amount).toBe(49900);
      expect(data.currency).toBe('INR');
      expect(data.status).toBe('pending');
      expect(data.qr_url).toBeTruthy();
      expect(data.upi_intent).toContain('upi://');
      expect(data.merchant.name).toBe(TEST_MERCHANT.name);
      expect(data.merchant.upi_id).toBe(TEST_MERCHANT.upi_id);
    });

    it('should return 404 for non-existent payment', async () => {
      repos.paymentRepo.findById.mockResolvedValue(null);

      const res = await app.inject({
        method: 'GET',
        url: '/v1/checkout/nonexistent',
      });

      expect(res.statusCode).toBe(404);
    });
  });
});
