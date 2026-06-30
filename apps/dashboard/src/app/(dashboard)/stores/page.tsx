'use client';

import { useEffect, useState } from 'react';
import { formatDate } from '@/components/format';

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

export default function StoresPage() {
  const [stores, setStores] = useState<any[]>([]);
  const [newName, setNewName] = useState('');

  useEffect(() => {
    apiFetch<{ data: any[] }>('/v1/stores').then((r) => setStores(r.data)).catch(() => {});
  }, []);

  const createStore = async () => {
    if (!newName.trim()) return;
    const result = await apiFetch<{ data: any }>('/v1/stores', {
      method: 'POST',
      body: JSON.stringify({ name: newName }),
    });
    setStores((prev) => [...prev, result.data]);
    setNewName('');
  };

  return (
    <div>
      <h1 className="text-2xl font-bold mb-6">Stores</h1>

      <div className="flex gap-2 mb-6">
        <input
          value={newName}
          onChange={(e) => setNewName(e.target.value)}
          placeholder="New store name"
          className="border rounded-lg px-4 py-2 text-sm flex-1"
        />
        <button onClick={createStore} className="bg-blue-600 text-white px-4 py-2 rounded-lg text-sm hover:bg-blue-700">
          Add Store
        </button>
      </div>

      <div className="bg-white rounded-xl border border-gray-200">
        <table className="w-full">
          <thead>
            <tr className="border-b border-gray-100">
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Name</th>
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">UPI ID</th>
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Active</th>
              <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Created</th>
            </tr>
          </thead>
          <tbody>
            {stores.map((s) => (
              <tr key={s.id} className="border-b border-gray-50 hover:bg-gray-50">
                <td className="px-5 py-3 text-sm font-medium">{s.name}</td>
                <td className="px-5 py-3 text-sm font-mono">{s.upi_id || '-'}</td>
                <td className="px-5 py-3 text-sm">
                  <span className={`inline-block w-2 h-2 rounded-full ${s.is_active ? 'bg-green-500' : 'bg-red-500'}`} />
                </td>
                <td className="px-5 py-3 text-sm text-gray-500">{formatDate(s.created_at)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
