import { describe, it, expect } from 'vitest';
import { createHmac } from 'node:crypto';
import { verifyWebhookSignature } from '../src/webhook.js';

function sign(body: string, secret: string): string {
  return createHmac('sha256', secret).update(body).digest('hex');
}

describe('verifyWebhookSignature', () => {
  const secret = 'whsec_test_secret_1234567890';
  const body = JSON.stringify({
    event: 'payment.success',
    data: { id: 'pay_123', amount: 10000 },
  });

  it('should return true for a valid signature', () => {
    const signature = sign(body, secret);
    expect(verifyWebhookSignature(body, signature, secret)).toBe(true);
  });

  it('should return false for an invalid signature', () => {
    expect(verifyWebhookSignature(body, 'invalid_signature_hex_string_1234', secret)).toBe(false);
  });

  it('should return false for a wrong secret', () => {
    const signature = sign(body, secret);
    expect(verifyWebhookSignature(body, signature, 'wrong_secret')).toBe(false);
  });

  it('should return false for a tampered body', () => {
    const signature = sign(body, secret);
    const tampered = body.replace('10000', '99999');
    expect(verifyWebhookSignature(tampered, signature, secret)).toBe(false);
  });

  it('should return false for a signature with different length', () => {
    expect(verifyWebhookSignature(body, 'short', secret)).toBe(false);
  });

  it('should handle empty body', () => {
    const emptySignature = sign('', secret);
    expect(verifyWebhookSignature('', emptySignature, secret)).toBe(true);
  });
});
