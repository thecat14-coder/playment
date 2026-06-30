'use client';

import type { CheckoutData } from '@/lib/api';
import { usePaymentStatus } from '@/hooks/use-payment-status';
import { QrDisplay } from '@/components/qr-display';
import { UpiButtons } from '@/components/upi-buttons';
import { CountdownTimer } from '@/components/countdown-timer';
import { PaymentStatusDisplay } from '@/components/payment-status';

export function CheckoutClient({ data }: { data: CheckoutData }) {
  const status = usePaymentStatus(data.payment_id, data.status);

  const amountFormatted = new Intl.NumberFormat('en-IN', {
    style: 'currency',
    currency: data.currency,
  }).format(data.amount / 100);

  if (status !== 'pending') {
    return (
      <div className="min-h-screen flex items-center justify-center p-4">
        <div className="w-full max-w-md bg-white rounded-2xl shadow-lg border border-gray-100 overflow-hidden">
          {data.merchant && (
            <div className="px-6 pt-6 flex items-center gap-3">
              {data.merchant.logo_url && (
                <img src={data.merchant.logo_url} alt="" className="w-8 h-8 rounded-full" />
              )}
              <span className="text-sm font-medium text-gray-700">{data.merchant.name}</span>
            </div>
          )}
          <PaymentStatusDisplay status={status} amount={data.amount} currency={data.currency} />
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen flex items-center justify-center p-4">
      <div className="w-full max-w-md bg-white rounded-2xl shadow-lg border border-gray-100 overflow-hidden">
        {/* Header */}
        <div className="px-6 pt-6 pb-4 border-b border-gray-100">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-3">
              {data.merchant?.logo_url && (
                <img src={data.merchant.logo_url} alt="" className="w-10 h-10 rounded-full" />
              )}
              <div>
                <h1 className="font-semibold text-gray-900">{data.merchant?.name ?? 'Merchant'}</h1>
                {data.customer_name && (
                  <p className="text-xs text-gray-500">for {data.customer_name}</p>
                )}
              </div>
            </div>
            <CountdownTimer expiresAt={data.expires_at} />
          </div>
        </div>

        {/* Amount */}
        <div className="px-6 py-5 text-center">
          <p className="text-sm text-gray-500 mb-1">Amount to pay</p>
          <p className="text-4xl font-bold text-gray-900">{amountFormatted}</p>
          <p className="text-xs text-gray-400 mt-1">Order: {data.order_id}</p>
        </div>

        {/* QR + UPI */}
        <div className="px-6 pb-6 space-y-5">
          <QrDisplay qrUrl={data.qr_url} merchantName={data.merchant?.name ?? 'Merchant'} />
          <UpiButtons upiIntent={data.upi_intent} upiId={data.merchant?.upi_id ?? ''} />
        </div>

        {/* Waiting indicator */}
        <div className="px-6 py-4 bg-gray-50 border-t border-gray-100">
          <div className="flex items-center justify-center gap-2 text-sm text-gray-500">
            <div className="w-2 h-2 rounded-full bg-green-400 animate-pulse" />
            Waiting for payment...
          </div>
        </div>
      </div>
    </div>
  );
}
