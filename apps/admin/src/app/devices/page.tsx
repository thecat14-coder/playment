'use client';

import { useEffect, useState } from 'react';
import { adminApi } from '@/lib/api';

export default function DevicesPage() {
  const [devices, setDevices] = useState<any[]>([]);

  useEffect(() => {
    adminApi.devices.list().then((r) => setDevices(r.data)).catch(() => {});
  }, []);

  const handleDisable = async (id: string) => {
    await adminApi.devices.disable(id);
    setDevices((prev) => prev.map((d) => d.id === id ? { ...d, status: 'suspended' } : d));
  };

  return (
    <div className="p-8">
      <h1 className="text-2xl font-bold mb-6">Devices</h1>
      <table className="w-full bg-white rounded-lg shadow">
        <thead className="bg-gray-100">
          <tr>
            <th className="p-3 text-left">Model</th>
            <th className="p-3 text-left">Status</th>
            <th className="p-3 text-left">Online</th>
            <th className="p-3 text-left">Health</th>
            <th className="p-3 text-left">Last Heartbeat</th>
            <th className="p-3 text-left">Actions</th>
          </tr>
        </thead>
        <tbody>
          {devices.map((d) => (
            <tr key={d.id} className="border-t">
              <td className="p-3">{d.model}</td>
              <td className="p-3">{d.status}</td>
              <td className="p-3">{d.is_online ? 'Online' : 'Offline'}</td>
              <td className="p-3">{d.health_score}</td>
              <td className="p-3">{d.last_heartbeat_at ? new Date(d.last_heartbeat_at).toLocaleString() : 'Never'}</td>
              <td className="p-3">
                {d.status === 'active' && (
                  <button onClick={() => handleDisable(d.id)} className="text-red-600 hover:underline">
                    Disable
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
