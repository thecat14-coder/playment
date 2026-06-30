'use client';

import { useEffect, useState } from 'react';
import { adminApi } from '@/lib/api';

export default function AdminDashboard() {
  const [summary, setSummary] = useState<any>(null);

  useEffect(() => {
    adminApi.health.overview().then((r) => setSummary(r.data)).catch(() => {});
  }, []);

  return (
    <div className="min-h-screen p-8">
      <h1 className="text-3xl font-bold mb-8">Admin Dashboard</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
        <a href="/merchants" className="p-4 bg-white rounded-lg shadow hover:shadow-md">
          <h2 className="text-lg font-semibold">Merchants</h2>
          <p className="text-2xl font-bold text-blue-600">{Array.isArray(summary) ? summary.length : '...'}</p>
        </a>
        <a href="/devices" className="p-4 bg-white rounded-lg shadow hover:shadow-md">
          <h2 className="text-lg font-semibold">Devices</h2>
          <p className="text-2xl font-bold text-green-600">Manage</p>
        </a>
        <a href="/reviews" className="p-4 bg-white rounded-lg shadow hover:shadow-md">
          <h2 className="text-lg font-semibold">Reviews</h2>
          <p className="text-2xl font-bold text-amber-600">Pending</p>
        </a>
        <a href="/health" className="p-4 bg-white rounded-lg shadow hover:shadow-md">
          <h2 className="text-lg font-semibold">Health</h2>
          <p className="text-2xl font-bold text-red-600">System</p>
        </a>
      </div>
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
        <a href="/evidence" className="p-4 bg-white rounded-lg shadow hover:shadow-md">
          <h2 className="text-lg font-semibold">Evidence</h2>
        </a>
        <a href="/payments" className="p-4 bg-white rounded-lg shadow hover:shadow-md">
          <h2 className="text-lg font-semibold">Payments</h2>
        </a>
        <a href="/audit" className="p-4 bg-white rounded-lg shadow hover:shadow-md">
          <h2 className="text-lg font-semibold">Audit Logs</h2>
        </a>
      </div>
    </div>
  );
}
