import { describe, it, expect } from 'vitest';
import { UpiService } from '../../src/services/upi.service.js';

describe('UpiService', () => {
  const upiService = new UpiService();

  it('should build a valid UPI intent string', () => {
    const intent = upiService.buildIntent({
      upiId: 'merchant@upi',
      merchantName: 'Test Store',
      amount: 49900,
      transactionRef: '01KWFPAY4Q4G97PC9ZAWSG5VKF',
      note: 'ORD-001',
    });

    expect(intent).toBe(
      'upi://pay?pa=merchant@upi&pn=Test%20Store&am=499.00&cu=INR&tr=01KWFPAY4Q4G97PC9ZAWSG5VKF&tn=ORD-001',
    );
  });

  it('should convert paise to rupees with two decimals', () => {
    const intent = upiService.buildIntent({
      upiId: 'test@upi',
      merchantName: 'Shop',
      amount: 100,
      transactionRef: 'PAY123',
    });
    expect(intent).toContain('am=1.00');
  });

  it('should handle amounts with paise precision', () => {
    const intent = upiService.buildIntent({
      upiId: 'test@upi',
      merchantName: 'Shop',
      amount: 12345,
      transactionRef: 'PAY123',
    });
    expect(intent).toContain('am=123.45');
  });

  it('should encode special characters in merchant name', () => {
    const intent = upiService.buildIntent({
      upiId: 'test@upi',
      merchantName: 'My Store & Co',
      amount: 100,
      transactionRef: 'PAY123',
    });
    expect(intent).toContain('pn=My%20Store%20%26%20Co');
  });

  it('should keep @ literal in pa and trim whitespace', () => {
    const intent = upiService.buildIntent({
      upiId: '  merchant@ybl  ',
      merchantName: 'Shop',
      amount: 1000,
      transactionRef: 'PAY123',
    });
    expect(intent).toContain('pa=merchant@ybl');
  });

  it('should sanitize transaction reference to allowed characters', () => {
    const intent = upiService.buildIntent({
      upiId: 'test@upi',
      merchantName: 'Shop',
      amount: 100,
      transactionRef: 'order_123 test!',
      note: 'order_123 test!',
    });
    expect(intent).toContain('tr=order123test');
    expect(intent).toContain('tn=order_123%20test!');
  });
});
