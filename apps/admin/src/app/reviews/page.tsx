'use client';

import { useEffect, useState } from 'react';
import { adminApi } from '@/lib/api';

export default function ReviewsPage() {
  const [reviews, setReviews] = useState<any[]>([]);

  useEffect(() => {
    adminApi.reviews.list().then((r) => setReviews(r.data)).catch(() => {});
  }, []);

  const handleAction = async (id: string, action: 'approve' | 'reject' | 'force-fail') => {
    const reason = window.prompt(`Reason for ${action}?`);
    if (action === 'approve') await adminApi.reviews.approve(id, reason || undefined);
    else if (action === 'reject') await adminApi.reviews.reject(id, reason || undefined);
    else if (action === 'force-fail') await adminApi.reviews.forceFail(id, reason || undefined);
    setReviews((prev) => prev.filter((r) => r.id !== id));
  };

  return (
    <div className="p-8">
      <h1 className="text-2xl font-bold mb-6">Manual Reviews</h1>
      <table className="w-full bg-white rounded-lg shadow">
        <thead className="bg-gray-100">
          <tr>
            <th className="p-3 text-left">Payment ID</th>
            <th className="p-3 text-left">Evidence ID</th>
            <th className="p-3 text-left">Created</th>
            <th className="p-3 text-left">Actions</th>
          </tr>
        </thead>
        <tbody>
          {reviews.map((r) => (
            <tr key={r.id} className="border-t">
              <td className="p-3 font-mono text-sm">{r.payment_id}</td>
              <td className="p-3 font-mono text-sm">{r.evidence_id || '-'}</td>
              <td className="p-3">{new Date(r.created_at).toLocaleString()}</td>
              <td className="p-3 space-x-2">
                <button onClick={() => handleAction(r.id, 'approve')} className="text-green-600 hover:underline">
                  Approve
                </button>
                <button onClick={() => handleAction(r.id, 'reject')} className="text-amber-600 hover:underline">
                  Reject
                </button>
                <button onClick={() => handleAction(r.id, 'force-fail')} className="text-red-600 hover:underline">
                  Force Fail
                </button>
              </td>
            </tr>
          ))}
          {reviews.length === 0 && (
            <tr>
              <td colSpan={4} className="p-6 text-center text-gray-500">No pending reviews</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}
