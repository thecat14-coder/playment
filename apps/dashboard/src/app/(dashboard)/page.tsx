'use client';

import { useEffect, useState } from 'react';
import { api } from '@/lib/api';
import { StatusBadge } from '@/components/status-badge';
import { formatAmount, formatDate } from '@/components/format';

export default function DashboardPage() {
  const [account, setAccount] = useState<any>(null);
  const [recentPayments, setRecentPayments] = useState<any[]>([]);
  const [stats, setStats] = useState({ total: 0, successful: 0, volume: 0 });

  useEffect(() => {
    api.account.get().then((r) => setAccount(r.data)).catch(() => {});
    api.payments.list({ limit: '5' }).then((r) => {
      setRecentPayments(r.data);
      setStats({
        total: r.pagination?.total ?? 0,
        successful: r.data.filter((p: any) => p.status === 'success').length,
        volume: r.data
          .filter((p: any) => p.status === 'success')
          .reduce((sum: number, p: any) => sum + p.amount, 0),
      });
    }).catch(() => {});
  }, []);

  return (
    <div>
      <h1 className="text-2xl font-bold mb-6">
        {account ? `Welcome, ${account.name}` : 'Dashboard'}
      </h1>

      {/* Stats */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8">
        <div className="bg-white rounded-xl border border-gray-200 p-5">
          <p className="text-sm text-gray-500 mb-1">Total Payments</p>
          <p className="text-3xl font-bold">{stats.total}</p>
        </div>
        <div className="bg-white rounded-xl border border-gray-200 p-5">
          <p className="text-sm text-gray-500 mb-1">Successful</p>
          <p className="text-3xl font-bold text-green-600">{stats.successful}</p>
        </div>
        <div className="bg-white rounded-xl border border-gray-200 p-5">
          <p className="text-sm text-gray-500 mb-1">Volume</p>
          <p className="text-3xl font-bold">{formatAmount(stats.volume)}</p>
        </div>
      </div>

      {/* Recent Payments */}
      <div className="bg-white rounded-xl border border-gray-200">
        <div className="px-5 py-4 border-b border-gray-100">
          <h2 className="font-semibold">Recent Payments</h2>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-100">
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Order ID</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Amount</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Status</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Date</th>
              </tr>
            </thead>
            <tbody>
              {recentPayments.map((p: any) => (
                <tr key={p.id} className="border-b border-gray-50 hover:bg-gray-50">
                  <td className="px-5 py-3 text-sm font-mono">{p.order_id}</td>
                  <td className="px-5 py-3 text-sm font-medium">{formatAmount(p.amount)}</td>
                  <td className="px-5 py-3"><StatusBadge status={p.status} /></td>
                  <td className="px-5 py-3 text-sm text-gray-500">{formatDate(p.created_at)}</td>
                </tr>
              ))}
              {recentPayments.length === 0 && (
                <tr>
                  <td colSpan={4} className="px-5 py-8 text-center text-sm text-gray-400">
                    No payments yet
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
