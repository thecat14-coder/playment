import { describe, it, expect, beforeAll } from 'vitest';
import { AuthService } from '../../src/services/auth.service.js';
import { getEnv } from '../../src/config/env.js';

describe('AuthService', () => {
  let authService: AuthService;

  beforeAll(() => {
    authService = new AuthService(getEnv());
  });

  describe('password hashing', () => {
    it('should hash and verify a password', async () => {
      const hash = await authService.hashPassword('mypassword123');
      expect(hash).toBeTruthy();
      expect(hash).not.toBe('mypassword123');

      const valid = await authService.verifyPassword(hash, 'mypassword123');
      expect(valid).toBe(true);
    });

    it('should reject wrong password', async () => {
      const hash = await authService.hashPassword('correct');
      const valid = await authService.verifyPassword(hash, 'wrong');
      expect(valid).toBe(false);
    });

    it('should produce different hashes for same password', async () => {
      const hash1 = await authService.hashPassword('same');
      const hash2 = await authService.hashPassword('same');
      expect(hash1).not.toBe(hash2);
    });
  });

  describe('JWT tokens', () => {
    it('should generate and verify access token', () => {
      const token = authService.generateAccessToken('merchant_123');
      const payload = authService.verifyAccessToken(token);
      expect(payload.sub).toBe('merchant_123');
    });

    it('should reject tampered access token', () => {
      const token = authService.generateAccessToken('merchant_123');
      expect(() => authService.verifyAccessToken(token + 'x')).toThrow();
    });

    it('should generate and verify refresh token', () => {
      const token = authService.generateRefreshToken('merchant_123');
      const payload = authService.verifyRefreshToken(token);
      expect(payload.sub).toBe('merchant_123');
    });

    it('should reject access token used as refresh token', () => {
      const token = authService.generateAccessToken('merchant_123');
      expect(() => authService.verifyRefreshToken(token)).toThrow();
    });
  });

  describe('API key generation', () => {
    it('should generate key with correct prefix', () => {
      const { key, hash, prefix } = authService.generateApiKey();
      expect(key).toMatch(/^gw_live_/);
      expect(hash).toHaveLength(64);
      expect(prefix).toBe(key.slice(0, 16));
    });

    it('should produce unique keys each call', () => {
      const a = authService.generateApiKey();
      const b = authService.generateApiKey();
      expect(a.key).not.toBe(b.key);
      expect(a.hash).not.toBe(b.hash);
    });

    it('should hash consistently', () => {
      const { key } = authService.generateApiKey();
      const hash1 = authService.hashApiKey(key);
      const hash2 = authService.hashApiKey(key);
      expect(hash1).toBe(hash2);
    });
  });

  describe('webhook secret', () => {
    it('should generate 64-char hex string', () => {
      const secret = authService.generateWebhookSecret();
      expect(secret).toHaveLength(64);
      expect(secret).toMatch(/^[a-f0-9]+$/);
    });
  });
});
