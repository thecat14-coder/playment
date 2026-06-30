'use client';

import { useEffect, useState } from 'react';
import { usePathname } from 'next/navigation';
import { fetchCheckoutData, type CheckoutData } from '@/lib/api';
import { CheckoutClient } from '@/components/checkout-client';
import NotFound from './not-found';

export default function CheckoutPage() {
  const pathname = usePathname();
  const paymentId = pathname.replace(/^\//, '').split('/').filter(Boolean)[0] ?? '';

  const [data, setData] = useState<CheckoutData | null>(null);
  const [notFound, setNotFound] = useState(false);
  const [loading, setLoading] = useState(Boolean(paymentId));

  useEffect(() => {
    if (!paymentId) {
      setLoading(false);
      setNotFound(true);
      return;
    }

    let cancelled = false;
    setLoading(true);
    setNotFound(false);

    fetchCheckoutData(paymentId)
      .then((checkout) => {
        if (!cancelled) setData(checkout);
      })
      .catch(() => {
        if (!cancelled) setNotFound(true);
      })
      .finally(() => {
        if (!cancelled) setLoading(false);
      });

    return () => {
      cancelled = true;
    };
  }, [paymentId]);

  if (!paymentId || notFound) return <NotFound />;

  if (loading || !data) {
    return (
      <div className="min-h-screen flex items-center justify-center p-4">
        <div className="text-sm text-gray-500">Loading payment...</div>
      </div>
    );
  }

  return <CheckoutClient data={data} />;
}
