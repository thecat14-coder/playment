'use client';

import { useEffect, useState } from 'react';
import { formatDate, formatAmount } from '@/components/format';

const BACKEND_URL = process.env.NEXT_PUBLIC_BACKEND_URL || 'http://localhost:3001';

async function apiFetch<T>(path: string, options: RequestInit = {}): Promise<T> {
  const token = localStorage.getItem('access_token');
  const headers: Record<string, string> = { 'Content-Type': 'application/json' };
  if (token) headers['Authorization'] = `Bearer ${token}`;
  const res = await fetch(`${BACKEND_URL}${path}`, { ...options, headers });
  const json = await res.json();
  if (!res.ok) throw new Error(json.error?.message || 'Request failed');
  return json;
}

export default function ReviewsPage() {
  const [reviews, setReviews] = useState<any[]>([]);

  useEffect(() => {
    apiFetch<{ data: any[]; pagination: any }>('/v1/reviews').then((r) => setReviews(r.data)).catch(() => {});
  }, []);

  const handleAction = async (id: string, action: 'approve' | 'reject') => {
    await apiFetch(`/v1/reviews/${id}/${action}`, { method: 'POST' });
    setReviews((prev) => prev.filter((r) => r.id !== id));
  };

  return (
    <div>
      <h1 className="text-2xl font-bold mb-6">Manual Reviews</h1>
      <div className="bg-white rounded-xl border border-gray-200">
        <table className="w-full">
          <thead>
            <tr className="border-b border-gray-100">
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Payment</th>
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Created</th>
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Actions</th>
            </tr>
          </thead>
          <tbody>
            {reviews.map((r) => (
              <tr key={r.id} className="border-b border-gray-50 hover:bg-gray-50">
                <td className="px-5 py-3 text-sm font-mono">{r.payment_id}</td>
                <td className="px-5 py-3 text-sm text-gray-500">{formatDate(r.created_at)}</td>
                <td className="px-5 py-3 text-sm space-x-3">
                  <button onClick={() => handleAction(r.id, 'approve')} className="text-green-600 hover:underline">
                    Approve
                  </button>
                  <button onClick={() => handleAction(r.id, 'reject')} className="text-red-600 hover:underline">
                    Reject
                  </button>
                </td>
              </tr>
            ))}
            {reviews.length === 0 && (
              <tr>
                <td colSpan={3} className="px-5 py-8 text-center text-sm text-gray-400">
                  No pending reviews
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
