import { describe, it, expect, beforeAll, afterAll, beforeEach } from 'vitest';
import { createTestApp } from '../helpers/test-app.js';
import { TEST_MERCHANT } from '../helpers/fixtures.js';
import type { FastifyInstance } from 'fastify';

describe('Account Routes', () => {
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
    repos.merchantRepo.update.mockReset();
  });

  describe('GET /v1/account', () => {
    it('should return account details', async () => {
      repos.merchantRepo.findById.mockResolvedValue(TEST_MERCHANT);

      const res = await app.inject({
        method: 'GET',
        url: '/v1/account',
        headers: getAuthHeader(TEST_MERCHANT.id),
      });

      expect(res.statusCode).toBe(200);
      const data = res.json().data;
      expect(data.id).toBe(TEST_MERCHANT.id);
      expect(data.name).toBe(TEST_MERCHANT.name);
      expect(data.email).toBe(TEST_MERCHANT.email);
      expect(data).not.toHaveProperty('password_hash');
    });

    it('should return 401 without auth', async () => {
      const res = await app.inject({
        method: 'GET',
        url: '/v1/account',
      });
      expect(res.statusCode).toBe(401);
    });
  });

  describe('PATCH /v1/account', () => {
    it('should update account fields', async () => {
      repos.merchantRepo.update.mockResolvedValue({
        ...TEST_MERCHANT,
        name: 'Updated Store',
      });

      const res = await app.inject({
        method: 'PATCH',
        url: '/v1/account',
        headers: getAuthHeader(TEST_MERCHANT.id),
        payload: { name: 'Updated Store' },
      });

      expect(res.statusCode).toBe(200);
      expect(res.json().data.name).toBe('Updated Store');
    });

    it('should reject non-HTTPS webhook URL', async () => {
      const res = await app.inject({
        method: 'PATCH',
        url: '/v1/account',
        headers: getAuthHeader(TEST_MERCHANT.id),
        payload: { webhook_url: 'http://insecure.com/hook' },
      });

      expect(res.statusCode).toBe(400);
    });
  });
});
