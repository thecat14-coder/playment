import { fetchCheckoutData } from '@/lib/api';
import { CheckoutClient } from './checkout-client';
import { notFound } from 'next/navigation';

export default async function CheckoutPage({ params }: { params: Promise<{ paymentId: string }> }) {
  const { paymentId } = await params;

  let data;
  try {
    data = await fetchCheckoutData(paymentId);
  } catch {
    notFound();
  }

  return <CheckoutClient data={data} />;
}
