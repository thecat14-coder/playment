'use client';

import { useEffect, useState } from 'react';

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

export default function HealthPage() {
  const [summary, setSummary] = useState<any>(null);

  useEffect(() => {
    apiFetch<{ data: any }>('/v1/health/summary').then((r) => setSummary(r.data)).catch(() => {});
  }, []);

  if (!summary) return <div className="text-gray-500">Loading health data...</div>;

  const levelColors: Record<string, string> = {
    excellent: 'text-green-600 bg-green-50',
    good: 'text-blue-600 bg-blue-50',
    warning: 'text-amber-600 bg-amber-50',
    critical: 'text-red-600 bg-red-50',
  };

  return (
    <div>
      <h1 className="text-2xl font-bold mb-6">Health Dashboard</h1>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8">
        <div className="bg-white rounded-xl border border-gray-200 p-5">
          <p className="text-sm text-gray-500 mb-1">Health Score</p>
          <p className={`text-3xl font-bold ${summary.health_level === 'critical' ? 'text-red-600' : summary.health_level === 'warning' ? 'text-amber-600' : 'text-green-600'}`}>
            {summary.health_score}
          </p>
          <span className={`inline-block mt-1 px-2 py-0.5 rounded text-xs font-medium ${levelColors[summary.health_level] || ''}`}>
            {summary.health_level}
          </span>
        </div>
        <div className="bg-white rounded-xl border border-gray-200 p-5">
          <p className="text-sm text-gray-500 mb-1">Active Devices</p>
          <p className="text-3xl font-bold">{summary.active_devices}</p>
        </div>
        <div className="bg-white rounded-xl border border-gray-200 p-5">
          <p className="text-sm text-gray-500 mb-1">Online Devices</p>
          <p className="text-3xl font-bold text-green-600">{summary.online_devices}</p>
        </div>
      </div>

      <div className="bg-white rounded-xl border border-gray-200">
        <div className="px-5 py-4 border-b border-gray-100">
          <h2 className="font-semibold">Device Health Details</h2>
        </div>
        <table className="w-full">
          <thead>
            <tr className="border-b border-gray-100">
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Device</th>
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Health</th>
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Status</th>
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Last Heartbeat</th>
            </tr>
          </thead>
          <tbody>
            {summary.devices?.map((d: any) => (
              <tr key={d.device_id} className="border-b border-gray-50 hover:bg-gray-50">
                <td className="px-5 py-3 text-sm">{d.model}</td>
                <td className="px-5 py-3 text-sm">
                  <span className={`font-medium ${d.health_level === 'critical' ? 'text-red-600' : d.health_level === 'warning' ? 'text-amber-600' : d.health_level === 'good' ? 'text-blue-600' : 'text-green-600'}`}>
                    {d.health_score}
                  </span>
                </td>
                <td className="px-5 py-3 text-sm">
                  <span className={`inline-block w-2 h-2 rounded-full ${d.is_online ? 'bg-green-500' : 'bg-gray-300'}`} />
                  {' '}{d.is_online ? 'Online' : 'Offline'}
                </td>
                <td className="px-5 py-3 text-sm text-gray-500">
                  {d.last_heartbeat_at ? new Date(d.last_heartbeat_at).toLocaleString() : 'Never'}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
