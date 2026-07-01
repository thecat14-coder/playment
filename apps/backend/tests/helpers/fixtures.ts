export const TEST_MERCHANT = {
  id: '01JTEST00000000MERCHANT1',
  name: 'Test Store',
  email: 'test@example.com',
  password_hash: '$argon2id$v=19$m=65536,t=3,p=4$mock',
  upi_id: 'teststore@upi',
  webhook_url: 'https://example.com/webhook',
  webhook_secret: 'a'.repeat(64),
  logo_url: null,
  status: 'active' as const,
  created_at: new Date('2026-01-01'),
  updated_at: new Date('2026-01-01'),
};

export const TEST_PAYMENT = {
  id: '01JTEST00000000PAYMENT01',
  merchant_id: TEST_MERCHANT.id,
  amount: 49900,
  currency: 'INR',
  status: 'pending' as const,
  order_id: 'ORD-001',
  customer_name: 'Rahul',
  customer_email: 'rahul@example.com',
  customer_phone: '+919876543210',
  upi_intent: 'upi://pay?pa=teststore@upi&pn=Test%20Store&am=499.00&cu=INR&tr=01KWFPAY4Q4G97PC9ZAWSG5VKF&tn=ORD-001',
  qr_url: 'data:image/png;base64,mockqr',
  payment_link: 'http://localhost:3002/01JTEST00000000PAYMENT01',
  metadata: null,
  expires_at: new Date(Date.now() + 1800_000),
  paid_at: null,
  created_at: new Date('2026-01-01'),
};

export const TEST_SUCCESS_PAYMENT = {
  ...TEST_PAYMENT,
  id: '01JTEST00000000PAYMENT02',
  status: 'success' as const,
  paid_at: new Date('2026-01-01T01:00:00'),
};

export const TEST_API_KEY = {
  id: '01JTEST0000000000APIKEY1',
  merchant_id: TEST_MERCHANT.id,
  key_hash: 'abc123hash',
  key_prefix: 'gw_live_abcd1234',
  label: 'Test Key',
  last_used_at: null,
  is_active: true,
  created_at: new Date('2026-01-01'),
};

export function createPaymentLinkInput(overrides = {}) {
  return {
    amount: 49900,
    currency: 'INR' as const,
    order_id: 'ORD-001',
    customer_name: 'Rahul',
    expires_in: 1800,
    ...overrides,
  };
}
