import { describe, it, expect } from 'vitest';
import { UpiService } from '../../src/services/upi.service.js';

describe('UpiService', () => {
  const upiService = new UpiService();

  it('should build a valid UPI intent string', () => {
    const intent = upiService.buildIntent({
      upiId: 'merchant@upi',
      merchantName: 'Test Store',
      amount: 49900,
      orderId: 'ORD-001',
    });

    expect(intent).toContain('upi://pay?');
    expect(intent).toContain('pa=merchant%40upi');
    expect(intent).toContain('pn=Test+Store');
    expect(intent).toContain('am=499.00');
    expect(intent).toContain('cu=INR');
    expect(intent).toContain('tr=ORD-001');
    expect(intent).toContain('tn=ORD-001');
  });

  it('should convert paise to rupees correctly', () => {
    const intent = upiService.buildIntent({
      upiId: 'test@upi',
      merchantName: 'Shop',
      amount: 100,
      orderId: 'X',
    });
    expect(intent).toContain('am=1.00');
  });

  it('should handle amounts with paise precision', () => {
    const intent = upiService.buildIntent({
      upiId: 'test@upi',
      merchantName: 'Shop',
      amount: 12345,
      orderId: 'X',
    });
    expect(intent).toContain('am=123.45');
  });

  it('should encode special characters in merchant name', () => {
    const intent = upiService.buildIntent({
      upiId: 'test@upi',
      merchantName: 'My Store & Co',
      amount: 100,
      orderId: 'X',
    });
    expect(intent).toContain('pn=My+Store+%26+Co');
  });
});
