import { vi } from 'vitest';

export function createMockMerchantRepo() {
  return {
    findById: vi.fn(),
    findByEmail: vi.fn(),
    create: vi.fn(),
    update: vi.fn(),
  };
}

export function createMockPaymentRepo() {
  return {
    findById: vi.fn(),
    findByMerchantAndId: vi.fn(),
    create: vi.fn(),
    updateStatus: vi.fn(),
    listByMerchant: vi.fn(),
    findExpiredPending: vi.fn(),
  };
}

export function createMockPaymentEventRepo() {
  return {
    create: vi.fn(),
    findByPaymentId: vi.fn(),
  };
}

export function createMockApiKeyRepo() {
  return {
    findByHash: vi.fn(),
    create: vi.fn(),
    listByMerchant: vi.fn(),
    update: vi.fn(),
    updateLastUsed: vi.fn(),
  };
}

export function createMockWebhookLogRepo() {
  return {
    create: vi.fn(),
    listByMerchant: vi.fn(),
    findByPaymentId: vi.fn(),
  };
}

export function createMockWebhookService() {
  return {
    enqueueDelivery: vi.fn(),
    enqueueTest: vi.fn(),
    getQueue: vi.fn(),
  };
}

export function createMockQrService() {
  return {
    generateDataUrl: vi.fn().mockResolvedValue('data:image/png;base64,mockqr'),
    generateBuffer: vi.fn().mockResolvedValue(Buffer.from('mockqr')),
  };
}

export function createMockUpiService() {
  return {
    buildIntent: vi.fn().mockReturnValue('upi://pay?pa=test@upi&pn=Test&am=100.00&cu=INR&tr=ORD1&tn=ORD1'),
  };
}
