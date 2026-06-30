'use client';

import { useEffect, useState } from 'react';
import { api } from '@/lib/api';

const V2_API = {
  devices: {
    list: (params?: Record<string, string>) => {
      const qs = params ? '?' + new URLSearchParams(params).toString() : '';
      return apiFetch<{ data: any[]; pagination: any }>(`/v1/devices${qs}`);
    },
    get: (id: string) => apiFetch<{ data: any }>(`/v1/devices/${id}`),
  },
};

async function apiFetch<T>(path: string, options: RequestInit = {}): Promise<T> {
  const token = localStorage.getItem('access_token');
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
  };
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const BACKEND_URL = process.env.NEXT_PUBLIC_BACKEND_URL || 'http://localhost:3001';
  const res = await fetch(`${BACKEND_URL}${path}`, { ...options, headers });
  const json = await res.json();
  if (!res.ok) throw new Error(json.error?.message || 'Request failed');
  return json;
}

export default function DevicesPage() {
  const [devices, setDevices] = useState<any[]>([]);

  useEffect(() => {
    V2_API.devices.list().then((r) => setDevices(r.data)).catch(() => {});
  }, []);

  return (
    <div>
      <h1 className="text-2xl font-bold mb-6">Devices</h1>
      <div className="bg-white rounded-xl border border-gray-200">
        <table className="w-full">
          <thead>
            <tr className="border-b border-gray-100">
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Model</th>
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Status</th>
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Online</th>
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Health</th>
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Last Heartbeat</th>
            </tr>
          </thead>
          <tbody>
            {devices.map((d) => (
              <tr key={d.id} className="border-b border-gray-50 hover:bg-gray-50">
                <td className="px-5 py-3 text-sm">{d.model}</td>
                <td className="px-5 py-3 text-sm">{d.status}</td>
                <td className="px-5 py-3 text-sm">
                  <span className={`inline-block w-2 h-2 rounded-full ${d.is_online ? 'bg-green-500' : 'bg-gray-300'}`} />
                  {' '}{d.is_online ? 'Online' : 'Offline'}
                </td>
                <td className="px-5 py-3 text-sm">{d.health_score}</td>
                <td className="px-5 py-3 text-sm text-gray-500">
                  {d.last_heartbeat_at ? new Date(d.last_heartbeat_at).toLocaleString() : 'Never'}
                </td>
              </tr>
            ))}
            {devices.length === 0 && (
              <tr>
                <td colSpan={5} className="px-5 py-8 text-center text-sm text-gray-400">
                  No devices registered. Install the Android app on your merchant device.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
