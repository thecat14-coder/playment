'use client';

export function PaymentStatusDisplay({ status, amount, currency }: { status: string; amount: number; currency: string }) {
  const amountFormatted = new Intl.NumberFormat('en-IN', {
    style: 'currency',
    currency,
  }).format(amount / 100);

  if (status === 'success') {
    return (
      <div className="flex flex-col items-center py-10 space-y-4">
        <div className="w-20 h-20 rounded-full bg-green-100 flex items-center justify-center">
          <svg className="w-10 h-10 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2.5} d="M5 13l4 4L19 7" />
          </svg>
        </div>
        <h2 className="text-2xl font-bold text-gray-900">Payment Successful</h2>
        <p className="text-3xl font-bold text-green-600">{amountFormatted}</p>
        <p className="text-sm text-gray-500">Your payment has been received</p>
      </div>
    );
  }

  if (status === 'failed') {
    return (
      <div className="flex flex-col items-center py-10 space-y-4">
        <div className="w-20 h-20 rounded-full bg-red-100 flex items-center justify-center">
          <svg className="w-10 h-10 text-red-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2.5} d="M6 18L18 6M6 6l12 12" />
          </svg>
        </div>
        <h2 className="text-2xl font-bold text-gray-900">Payment Failed</h2>
        <p className="text-sm text-gray-500">Something went wrong. Please try again.</p>
      </div>
    );
  }

  if (status === 'expired') {
    return (
      <div className="flex flex-col items-center py-10 space-y-4">
        <div className="w-20 h-20 rounded-full bg-gray-100 flex items-center justify-center">
          <svg className="w-10 h-10 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
        </div>
        <h2 className="text-2xl font-bold text-gray-900">Payment Expired</h2>
        <p className="text-sm text-gray-500">This payment link has expired</p>
      </div>
    );
  }

  return null;
}
