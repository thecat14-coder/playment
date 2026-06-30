const BACKEND_URL = process.env.NEXT_PUBLIC_BACKEND_URL || 'http://localhost:3001';

export interface CheckoutData {
  payment_id: string;
  amount: number;
  currency: string;
  status: string;
  order_id: string;
  customer_name: string | null;
  upi_intent: string;
  qr_url: string;
  expires_at: string;
  merchant: {
    name: string;
    logo_url: string | null;
    upi_id: string;
  } | null;
}

export async function fetchCheckoutData(paymentId: string): Promise<CheckoutData> {
  const res = await fetch(`${BACKEND_URL}/v1/checkout/${paymentId}`, {
    cache: 'no-store',
  });
  if (!res.ok) throw new Error('Payment not found');
  const json = await res.json();
  return json.data;
}

export function getWebSocketUrl(paymentId: string): string {
  const wsBase = BACKEND_URL.replace(/^http/, 'ws');
  return `${wsBase}/ws/payments/${paymentId}`;
}

export function getPollingUrl(paymentId: string): string {
  return `${BACKEND_URL}/v1/checkout/${paymentId}`;
}
