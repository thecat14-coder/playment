import { describe, it, expect, beforeAll, afterAll } from 'vitest';
import { createTestApp } from '../helpers/test-app.js';
import { TEST_MERCHANT } from '../helpers/fixtures.js';
import type { FastifyInstance } from 'fastify';

describe('Auth Routes', () => {
  let app: FastifyInstance;
  let repos: any;

  beforeAll(async () => {
    const testApp = await createTestApp();
    app = testApp.app;
    repos = testApp.repos;
  });

  afterAll(async () => { await app.close(); });

  describe('POST /v1/auth/register', () => {
    it('should register a new merchant', async () => {
      repos.merchantRepo.findByEmail.mockResolvedValue(null);
      repos.merchantRepo.create.mockImplementation(async (data: any) => ({
        ...data,
        created_at: new Date(),
        updated_at: new Date(),
      }));

      const res = await app.inject({
        method: 'POST',
        url: '/v1/auth/register',
        payload: {
          name: 'New Store',
          email: 'new@example.com',
          password: 'password123',
          upi_id: 'store@upi',
        },
      });

      expect(res.statusCode).toBe(201);
      const body = res.json();
      expect(body.data.merchant.name).toBe('New Store');
      expect(body.data.access_token).toBeTruthy();
      expect(body.data.refresh_token).toBeTruthy();
    });

    it('should return 409 for duplicate email', async () => {
      repos.merchantRepo.findByEmail.mockResolvedValue(TEST_MERCHANT);

      const res = await app.inject({
        method: 'POST',
        url: '/v1/auth/register',
        payload: {
          name: 'Dup',
          email: TEST_MERCHANT.email,
          password: 'password123',
          upi_id: 'dup@upi',
        },
      });

      expect(res.statusCode).toBe(409);
      expect(res.json().error.code).toBe('CONFLICT');
    });

    it('should return 400 for invalid email', async () => {
      const res = await app.inject({
        method: 'POST',
        url: '/v1/auth/register',
        payload: {
          name: 'Test',
          email: 'not-an-email',
          password: 'password123',
          upi_id: 'test@upi',
        },
      });

      expect(res.statusCode).toBe(400);
    });

    it('should return 400 for short password', async () => {
      const res = await app.inject({
        method: 'POST',
        url: '/v1/auth/register',
        payload: {
          name: 'Test',
          email: 'test@example.com',
          password: 'short',
          upi_id: 'test@upi',
        },
      });

      expect(res.statusCode).toBe(400);
    });
  });

  describe('POST /v1/auth/login', () => {
    it('should return 401 for bad credentials', async () => {
      repos.merchantRepo.findByEmail.mockResolvedValue(null);

      const res = await app.inject({
        method: 'POST',
        url: '/v1/auth/login',
        payload: { email: 'nobody@example.com', password: 'wrong' },
      });

      expect(res.statusCode).toBe(401);
    });
  });

  describe('POST /v1/auth/refresh', () => {
    it('should return 400 for missing refresh_token', async () => {
      const res = await app.inject({
        method: 'POST',
        url: '/v1/auth/refresh',
        payload: {},
      });

      expect(res.statusCode).toBe(400);
    });
  });
});
