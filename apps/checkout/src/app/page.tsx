'use client';

import { useEffect, useState } from 'react';
import { fetchCheckoutData, type CheckoutData } from '@/lib/api';
import { CheckoutClient } from '@/components/checkout-client';
import NotFound from './not-found';

function readPaymentIdFromUrl(): string {
  if (typeof window === 'undefined') return '';
  return window.location.pathname.replace(/^\//, '').split('/').filter(Boolean)[0] ?? '';
}

export default function CheckoutPage() {
  const [paymentId, setPaymentId] = useState('');
  const [data, setData] = useState<CheckoutData | null>(null);
  const [notFound, setNotFound] = useState(false);
  const [loadError, setLoadError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const id = readPaymentIdFromUrl();
    setPaymentId(id);

    if (!id) {
      setLoading(false);
      setNotFound(true);
      return;
    }

    let cancelled = false;
    setLoading(true);
    setNotFound(false);
    setLoadError(null);

    fetchCheckoutData(id)
      .then((checkout) => {
        if (!cancelled) setData(checkout);
      })
      .catch((err: Error) => {
        if (!cancelled) {
          setNotFound(true);
          setLoadError(err.message);
        }
      })
      .finally(() => {
        if (!cancelled) setLoading(false);
      });

    return () => {
      cancelled = true;
    };
  }, []);

  if (!paymentId || notFound) {
    return (
      <>
        <NotFound />
        {loadError && (
          <p className="text-center text-xs text-gray-400 px-4 pb-8">{loadError}</p>
        )}
      </>
    );
  }

  if (loading || !data) {
    return (
      <div className="min-h-screen flex items-center justify-center p-4">
        <div className="text-sm text-gray-500">Loading payment...</div>
      </div>
    );
  }

  return <CheckoutClient data={data} />;
}
