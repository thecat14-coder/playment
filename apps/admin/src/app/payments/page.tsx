'use client';

import { useEffect, useState } from 'react';

export default function PaymentsPage() {
  const [payments, setPayments] = useState<any[]>([]);

  useEffect(() => {
    fetch('/api/proxy/payments').then(r => r.json()).then(d => setPayments(d.data || [])).catch(() => {});
  }, []);

  return (
    <div className="p-8">
      <h1 className="text-2xl font-bold mb-6">Payments</h1>
      <table className="w-full bg-white rounded-lg shadow">
        <thead className="bg-gray-100">
          <tr>
            <th className="p-3 text-left">ID</th>
            <th className="p-3 text-left">Amount</th>
            <th className="p-3 text-left">Status</th>
            <th className="p-3 text-left">Order ID</th>
            <th className="p-3 text-left">Created</th>
          </tr>
        </thead>
        <tbody>
          {payments.map((p) => (
            <tr key={p.id} className="border-t">
              <td className="p-3 font-mono text-sm">{p.id}</td>
              <td className="p-3">₹{(p.amount / 100).toFixed(2)}</td>
              <td className="p-3">{p.status}</td>
              <td className="p-3">{p.order_id}</td>
              <td className="p-3">{new Date(p.created_at).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
