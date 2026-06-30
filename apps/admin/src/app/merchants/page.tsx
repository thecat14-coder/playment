'use client';

import { useEffect, useState } from 'react';
import { adminApi } from '@/lib/api';

export default function MerchantsPage() {
  const [merchants, setMerchants] = useState<any[]>([]);

  useEffect(() => {
    adminApi.merchants.list().then((r) => setMerchants(r.data)).catch(() => {});
  }, []);

  const handleSuspend = async (id: string) => {
    await adminApi.merchants.suspend(id);
    setMerchants((prev) => prev.map((m) => m.id === id ? { ...m, status: 'suspended' } : m));
  };

  return (
    <div className="p-8">
      <h1 className="text-2xl font-bold mb-6">Merchants</h1>
      <table className="w-full bg-white rounded-lg shadow">
        <thead className="bg-gray-100">
          <tr>
            <th className="p-3 text-left">Name</th>
            <th className="p-3 text-left">Email</th>
            <th className="p-3 text-left">Status</th>
            <th className="p-3 text-left">Health</th>
            <th className="p-3 text-left">Devices</th>
            <th className="p-3 text-left">Actions</th>
          </tr>
        </thead>
        <tbody>
          {merchants.map((m) => (
            <tr key={m.id} className="border-t">
              <td className="p-3">{m.name}</td>
              <td className="p-3">{m.email}</td>
              <td className="p-3">
                <span className={`px-2 py-1 rounded text-sm ${m.status === 'active' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>
                  {m.status}
                </span>
              </td>
              <td className="p-3">{m.health_score}</td>
              <td className="p-3">{m.active_device_count}</td>
              <td className="p-3">
                {m.status === 'active' && (
                  <button onClick={() => handleSuspend(m.id)} className="text-red-600 hover:underline">
                    Suspend
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
