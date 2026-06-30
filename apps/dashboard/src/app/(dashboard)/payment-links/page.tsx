'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { api } from '@/lib/api';
import { StatusBadge } from '@/components/status-badge';
import { formatAmount, formatDate } from '@/components/format';

function hasValidUpiId(upiId: string | null | undefined): boolean {
  const trimmed = upiId?.trim() ?? '';
  return trimmed.length >= 3 && trimmed.includes('@');
}

export default function PaymentLinksPage() {
  const router = useRouter();
  const [links, setLinks] = useState<any[]>([]);
  const [account, setAccount] = useState<any>(null);
  const [showCreate, setShowCreate] = useState(false);
  const [form, setForm] = useState({ amount: '', order_id: '', customer_name: '', expires_in: '1800' });
  const [creating, setCreating] = useState(false);
  const [created, setCreated] = useState<any>(null);
  const [error, setError] = useState('');

  const upiConfigured = account ? hasValidUpiId(account.upi_id) : false;

  useEffect(() => {
    loadLinks();
    api.account.get().then((r) => setAccount(r.data)).catch(() => {});
  }, []);

  const loadLinks = () => {
    api.paymentLinks.list().then((r) => setLinks(r.data)).catch(() => {});
  };

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!upiConfigured) {
      setError('Add your UPI ID in Settings before creating payment links.');
      return;
    }
    setCreating(true);
    setError('');
    try {
      const res = await api.paymentLinks.create({
        amount: Math.round(parseFloat(form.amount) * 100),
        order_id: form.order_id,
        customer_name: form.customer_name || undefined,
        expires_in: parseInt(form.expires_in),
      });
      setCreated(res.data);
      loadLinks();
    } catch (err: any) {
      const message = err.message || 'Failed to create payment link';
      setError(message);
      if (message.toLowerCase().includes('upi')) {
        router.push('/settings');
      }
    } finally {
      setCreating(false);
    }
  };

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-2xl font-bold">Payment Links</h1>
        <button
          onClick={() => {
            if (!upiConfigured) {
              router.push('/settings');
              return;
            }
            setShowCreate(!showCreate);
            setCreated(null);
            setError('');
          }}
          className="px-4 py-2 bg-brand-600 text-white rounded-lg text-sm font-medium hover:bg-brand-700 transition-colors"
        >
          {showCreate ? 'Cancel' : 'Create Payment Link'}
        </button>
      </div>

      {account && !upiConfigured && (
        <div className="mb-6 p-4 rounded-xl border border-amber-200 bg-amber-50 text-amber-900 text-sm">
          <p className="font-medium mb-1">UPI ID required</p>
          <p className="mb-3">Add your UPI ID before you can create payment links. Customers need it to pay you.</p>
          <Link href="/settings" className="text-brand-700 font-medium hover:underline">
            Configure UPI ID in Settings →
          </Link>
        </div>
      )}

      {showCreate && upiConfigured && (
        <div className="bg-white rounded-xl border border-gray-200 p-6 mb-6">
          {created ? (
            <div className="space-y-4">
              <h3 className="font-semibold text-green-600">Payment link created!</h3>
              <div className="space-y-2 text-sm">
                <p><span className="text-gray-500">Payment Link:</span>{' '}
                  <a href={created.payment_link} target="_blank" rel="noopener noreferrer" className="text-brand-600 underline break-all">{created.payment_link}</a>
                </p>
                <p><span className="text-gray-500">Amount:</span> {formatAmount(created.amount)}</p>
                <p><span className="text-gray-500">Status:</span> <StatusBadge status={created.status} /></p>
              </div>
              <button onClick={() => { setCreated(null); setForm({ amount: '', order_id: '', customer_name: '', expires_in: '1800' }); }}
                className="text-sm text-brand-600 font-medium hover:underline">Create another</button>
            </div>
          ) : (
            <form onSubmit={handleCreate} className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {error && (
                <div className="md:col-span-2 p-3 rounded-lg bg-red-50 text-red-600 text-sm">{error}</div>
              )}
              <div>
                <label className="block text-sm font-medium mb-1.5">Amount (INR)</label>
                <input type="number" step="0.01" min="1" required value={form.amount} onChange={(e) => setForm({ ...form, amount: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-brand-500 focus:border-brand-500 outline-none" />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1.5">Order ID</label>
                <input type="text" required value={form.order_id} onChange={(e) => setForm({ ...form, order_id: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-brand-500 focus:border-brand-500 outline-none" />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1.5">Customer Name (optional)</label>
                <input type="text" value={form.customer_name} onChange={(e) => setForm({ ...form, customer_name: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-brand-500 focus:border-brand-500 outline-none" />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1.5">Expires in</label>
                <select value={form.expires_in} onChange={(e) => setForm({ ...form, expires_in: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm">
                  <option value="900">15 minutes</option>
                  <option value="1800">30 minutes</option>
                  <option value="3600">1 hour</option>
                  <option value="86400">24 hours</option>
                </select>
              </div>
              <div className="md:col-span-2">
                <button type="submit" disabled={creating}
                  className="px-6 py-2 bg-brand-600 text-white rounded-lg text-sm font-medium hover:bg-brand-700 disabled:opacity-50 transition-colors">
                  {creating ? 'Creating...' : 'Create'}
                </button>
              </div>
            </form>
          )}
        </div>
      )}

      <div className="bg-white rounded-xl border border-gray-200">
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-100">
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Order ID</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Amount</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Customer</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Status</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Expires</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Created</th>
              </tr>
            </thead>
            <tbody>
              {links.map((l: any) => (
                <tr key={l.id} className="border-b border-gray-50 hover:bg-gray-50">
                  <td className="px-5 py-3 text-sm font-mono">{l.order_id}</td>
                  <td className="px-5 py-3 text-sm font-medium">{formatAmount(l.amount)}</td>
                  <td className="px-5 py-3 text-sm">{l.customer_name || '—'}</td>
                  <td className="px-5 py-3"><StatusBadge status={l.status} /></td>
                  <td className="px-5 py-3 text-sm text-gray-500">{formatDate(l.expires_at)}</td>
                  <td className="px-5 py-3 text-sm text-gray-500">{formatDate(l.created_at)}</td>
                </tr>
              ))}
              {links.length === 0 && (
                <tr><td colSpan={6} className="px-5 py-8 text-center text-sm text-gray-400">No payment links yet</td></tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
