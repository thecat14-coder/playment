import { describe, it, expect, beforeAll, afterAll, beforeEach } from 'vitest';
import { createTestApp } from '../helpers/test-app.js';
import { TEST_MERCHANT, TEST_PAYMENT, createPaymentLinkInput } from '../helpers/fixtures.js';
import type { FastifyInstance } from 'fastify';

describe('Payment Routes', () => {
  let app: FastifyInstance;
  let repos: any;
  let getAuthHeader: (id: string) => Record<string, string>;

  beforeAll(async () => {
    const testApp = await createTestApp();
    app = testApp.app;
    repos = testApp.repos;
    getAuthHeader = testApp.getAuthHeader;
  });

  afterAll(async () => { await app.close(); });

  beforeEach(() => {
    repos.merchantRepo.findById.mockReset();
    repos.paymentRepo.findById.mockReset();
    repos.paymentRepo.create.mockReset();
    repos.paymentRepo.listByMerchant.mockReset();
    repos.eventRepo.create.mockReset();
  });

  describe('POST /v1/payment-links', () => {
    it('should create a payment link', async () => {
      repos.merchantRepo.findById.mockResolvedValue(TEST_MERCHANT);
      repos.paymentRepo.create.mockImplementation(async (data: any) => ({
        ...data,
        created_at: new Date(),
      }));
      repos.eventRepo.create.mockResolvedValue({});

      const res = await app.inject({
        method: 'POST',
        url: '/v1/payment-links',
        headers: getAuthHeader(TEST_MERCHANT.id),
        payload: createPaymentLinkInput(),
      });

      expect(res.statusCode).toBe(201);
      const body = res.json();
      expect(body.data.amount).toBe(49900);
      expect(body.data.status).toBe('pending');
      expect(body.data.payment_link).toContain('http://localhost:3002/');
      expect(body.data.qr_url).toBeTruthy();
      expect(body.data.upi_intent).toContain('upi://');
    });

    it('should return 401 without auth', async () => {
      const res = await app.inject({
        method: 'POST',
        url: '/v1/payment-links',
        payload: createPaymentLinkInput(),
      });

      expect(res.statusCode).toBe(401);
    });

    it('should return 400 for amount below minimum', async () => {
      const res = await app.inject({
        method: 'POST',
        url: '/v1/payment-links',
        headers: getAuthHeader(TEST_MERCHANT.id),
        payload: { ...createPaymentLinkInput(), amount: 50 },
      });

      expect(res.statusCode).toBe(400);
    });

    it('should return 400 for amount above maximum', async () => {
      const res = await app.inject({
        method: 'POST',
        url: '/v1/payment-links',
        headers: getAuthHeader(TEST_MERCHANT.id),
        payload: { ...createPaymentLinkInput(), amount: 99_999_999 },
      });

      expect(res.statusCode).toBe(400);
    });

    it('should return 400 for missing order_id', async () => {
      const res = await app.inject({
        method: 'POST',
        url: '/v1/payment-links',
        headers: getAuthHeader(TEST_MERCHANT.id),
        payload: { amount: 10000 },
      });

      expect(res.statusCode).toBe(400);
    });
  });

  describe('GET /v1/payment-links/:id', () => {
    it('should return payment details for owner', async () => {
      repos.paymentRepo.findByMerchantAndId.mockResolvedValue(TEST_PAYMENT);

      const res = await app.inject({
        method: 'GET',
        url: `/v1/payment-links/${TEST_PAYMENT.id}`,
        headers: getAuthHeader(TEST_MERCHANT.id),
      });

      expect(res.statusCode).toBe(200);
      expect(res.json().data.id).toBe(TEST_PAYMENT.id);
    });

    it('should return 404 for non-existent payment', async () => {
      repos.paymentRepo.findByMerchantAndId.mockResolvedValue(null);

      const res = await app.inject({
        method: 'GET',
        url: '/v1/payment-links/nonexistent',
        headers: getAuthHeader(TEST_MERCHANT.id),
      });

      expect(res.statusCode).toBe(404);
    });
  });

  describe('GET /v1/payments', () => {
    it('should list payments with pagination', async () => {
      repos.paymentRepo.listByMerchant.mockResolvedValue({
        data: [TEST_PAYMENT],
        pagination: { page: 1, limit: 20, total: 1, total_pages: 1 },
      });

      const res = await app.inject({
        method: 'GET',
        url: '/v1/payments?page=1&limit=20',
        headers: getAuthHeader(TEST_MERCHANT.id),
      });

      expect(res.statusCode).toBe(200);
      const body = res.json();
      expect(body.data).toHaveLength(1);
      expect(body.pagination.total).toBe(1);
    });

    it('should filter by status', async () => {
      repos.paymentRepo.listByMerchant.mockResolvedValue({
        data: [],
        pagination: { page: 1, limit: 20, total: 0, total_pages: 0 },
      });

      const res = await app.inject({
        method: 'GET',
        url: '/v1/payments?status=success',
        headers: getAuthHeader(TEST_MERCHANT.id),
      });

      expect(res.statusCode).toBe(200);
      expect(repos.paymentRepo.listByMerchant).toHaveBeenCalledWith(
        TEST_MERCHANT.id,
        expect.objectContaining({ status: 'success' }),
      );
    });
  });
});
