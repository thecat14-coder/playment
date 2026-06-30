import { describe, it, expect } from 'vitest';
import { signWebhookPayload, verifyWebhookSignature } from '../../src/utils/webhook-signature.js';

describe('webhook-signature', () => {
  const secret = 'test-secret-key';
  const payload = JSON.stringify({ event: 'payment.success', amount: 100 });

  describe('signWebhookPayload', () => {
    it('should produce a hex string', () => {
      const sig = signWebhookPayload(payload, secret);
      expect(sig).toMatch(/^[a-f0-9]{64}$/);
    });

    it('should produce consistent signatures', () => {
      const a = signWebhookPayload(payload, secret);
      const b = signWebhookPayload(payload, secret);
      expect(a).toBe(b);
    });

    it('should produce different signatures for different payloads', () => {
      const a = signWebhookPayload('{"a":1}', secret);
      const b = signWebhookPayload('{"a":2}', secret);
      expect(a).not.toBe(b);
    });

    it('should produce different signatures for different secrets', () => {
      const a = signWebhookPayload(payload, 'secret1');
      const b = signWebhookPayload(payload, 'secret2');
      expect(a).not.toBe(b);
    });
  });

  describe('verifyWebhookSignature', () => {
    it('should return true for valid signature', () => {
      const sig = signWebhookPayload(payload, secret);
      expect(verifyWebhookSignature(payload, sig, secret)).toBe(true);
    });

    it('should return false for tampered payload', () => {
      const sig = signWebhookPayload(payload, secret);
      expect(verifyWebhookSignature(payload + 'x', sig, secret)).toBe(false);
    });

    it('should return false for wrong secret', () => {
      const sig = signWebhookPayload(payload, secret);
      expect(verifyWebhookSignature(payload, sig, 'wrong-secret')).toBe(false);
    });
  });
});
