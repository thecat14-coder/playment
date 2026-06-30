import { describe, it, expect, beforeEach, vi } from 'vitest';
import { MerchantService } from '../../src/services/merchant.service.js';
import { AuthService } from '../../src/services/auth.service.js';
import { createMockMerchantRepo } from '../helpers/mocks.js';
import { TEST_MERCHANT } from '../helpers/fixtures.js';
import { getEnv } from '../../src/config/env.js';

describe('MerchantService', () => {
  let service: MerchantService;
  let merchantRepo: ReturnType<typeof createMockMerchantRepo>;
  let authService: AuthService;

  beforeEach(() => {
    merchantRepo = createMockMerchantRepo();
    authService = new AuthService(getEnv());
    service = new MerchantService(merchantRepo as any, authService);
  });

  describe('register', () => {
    it('should create a merchant and return tokens', async () => {
      merchantRepo.findByEmail.mockResolvedValue(null);
      merchantRepo.create.mockImplementation(async (data: any) => ({
        ...data,
        created_at: new Date(),
        updated_at: new Date(),
      }));

      const result = await service.register({
        name: 'New Store',
        email: 'new@example.com',
        password: 'password123',
        upi_id: 'store@upi',
      });

      expect(result.merchant.name).toBe('New Store');
      expect(result.merchant.email).toBe('new@example.com');
      expect(result.access_token).toBeTruthy();
      expect(result.refresh_token).toBeTruthy();
      expect(merchantRepo.create).toHaveBeenCalledOnce();
    });

    it('should throw ConflictError if email already registered', async () => {
      merchantRepo.findByEmail.mockResolvedValue(TEST_MERCHANT);

      await expect(service.register({
        name: 'Dup',
        email: TEST_MERCHANT.email,
        password: 'password123',
        upi_id: 'dup@upi',
      })).rejects.toThrow('Email already registered');
    });
  });

  describe('login', () => {
    it('should return tokens for valid credentials', async () => {
      const hash = await authService.hashPassword('correct_password');
      merchantRepo.findByEmail.mockResolvedValue({ ...TEST_MERCHANT, password_hash: hash });

      const result = await service.login(TEST_MERCHANT.email, 'correct_password');

      expect(result.merchant.id).toBe(TEST_MERCHANT.id);
      expect(result.access_token).toBeTruthy();
      expect(result.refresh_token).toBeTruthy();
    });

    it('should throw for unknown email', async () => {
      merchantRepo.findByEmail.mockResolvedValue(null);
      await expect(service.login('nobody@example.com', 'pass'))
        .rejects.toThrow('Invalid credentials');
    });

    it('should throw for wrong password', async () => {
      const hash = await authService.hashPassword('correct');
      merchantRepo.findByEmail.mockResolvedValue({ ...TEST_MERCHANT, password_hash: hash });

      await expect(service.login(TEST_MERCHANT.email, 'wrong'))
        .rejects.toThrow('Invalid credentials');
    });
  });

  describe('refresh', () => {
    it('should issue new tokens from valid refresh token', async () => {
      const refreshToken = authService.generateRefreshToken(TEST_MERCHANT.id);
      merchantRepo.findById.mockResolvedValue(TEST_MERCHANT);

      const result = await service.refresh(refreshToken);
      expect(result.access_token).toBeTruthy();
      expect(result.refresh_token).toBeTruthy();
    });

    it('should throw for invalid refresh token', async () => {
      await expect(service.refresh('garbage')).rejects.toThrow();
    });
  });

  describe('getAccount', () => {
    it('should return account details without password hash', async () => {
      merchantRepo.findById.mockResolvedValue(TEST_MERCHANT);
      const result = await service.getAccount(TEST_MERCHANT.id);

      expect(result.id).toBe(TEST_MERCHANT.id);
      expect(result.email).toBe(TEST_MERCHANT.email);
      expect(result).not.toHaveProperty('password_hash');
      expect(result).not.toHaveProperty('webhook_secret');
    });

    it('should throw NotFoundError for unknown merchant', async () => {
      merchantRepo.findById.mockResolvedValue(null);
      await expect(service.getAccount('nonexistent'))
        .rejects.toThrow('Merchant not found');
    });
  });

  describe('updateAccount', () => {
    it('should update and return the merchant', async () => {
      merchantRepo.update.mockResolvedValue({ ...TEST_MERCHANT, name: 'Updated' });

      const result = await service.updateAccount(TEST_MERCHANT.id, { name: 'Updated' });
      expect(result.name).toBe('Updated');
      expect(merchantRepo.update).toHaveBeenCalledWith(TEST_MERCHANT.id, { name: 'Updated' });
    });
  });
});
