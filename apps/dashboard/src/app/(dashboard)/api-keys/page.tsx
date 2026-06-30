'use client';

import { useEffect, useState } from 'react';
import { api } from '@/lib/api';
import { formatDate } from '@/components/format';

export default function ApiKeysPage() {
  const [keys, setKeys] = useState<any[]>([]);
  const [newKey, setNewKey] = useState<string | null>(null);
  const [label, setLabel] = useState('');
  const [creating, setCreating] = useState(false);

  useEffect(() => { loadKeys(); }, []);

  const loadKeys = () => {
    api.apiKeys.list().then((r) => setKeys(r.data)).catch(() => {});
  };

  const handleCreate = async () => {
    setCreating(true);
    try {
      const res = await api.apiKeys.create(label ? { label } : undefined);
      setNewKey(res.data.key);
      setLabel('');
      loadKeys();
    } catch {
    } finally {
      setCreating(false);
    }
  };

  const handleDeactivate = async (id: string) => {
    await api.apiKeys.update(id, { is_active: false });
    loadKeys();
  };

  return (
    <div>
      <h1 className="text-2xl font-bold mb-6">API Keys</h1>

      {/* Create */}
      <div className="bg-white rounded-xl border border-gray-200 p-6 mb-6">
        <h2 className="font-semibold mb-4">Generate New API Key</h2>

        {newKey ? (
          <div className="space-y-3">
            <div className="p-4 bg-yellow-50 border border-yellow-200 rounded-lg">
              <p className="text-sm text-yellow-800 font-medium mb-2">Copy this key now — it won&apos;t be shown again.</p>
              <code className="block text-sm bg-white p-3 rounded border border-yellow-200 break-all select-all">{newKey}</code>
            </div>
            <button onClick={() => setNewKey(null)} className="text-sm text-brand-600 font-medium hover:underline">Done</button>
          </div>
        ) : (
          <div className="flex gap-3">
            <input
              type="text"
              placeholder="Label (optional)"
              value={label}
              onChange={(e) => setLabel(e.target.value)}
              className="flex-1 px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-brand-500 focus:border-brand-500 outline-none"
            />
            <button onClick={handleCreate} disabled={creating}
              className="px-4 py-2 bg-brand-600 text-white rounded-lg text-sm font-medium hover:bg-brand-700 disabled:opacity-50 transition-colors">
              {creating ? 'Generating...' : 'Generate'}
            </button>
          </div>
        )}
      </div>

      {/* List */}
      <div className="bg-white rounded-xl border border-gray-200">
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-100">
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Prefix</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Label</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Status</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Last Used</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Created</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3"></th>
              </tr>
            </thead>
            <tbody>
              {keys.map((k: any) => (
                <tr key={k.id} className="border-b border-gray-50">
                  <td className="px-5 py-3 text-sm font-mono">{k.key_prefix}...</td>
                  <td className="px-5 py-3 text-sm">{k.label || '—'}</td>
                  <td className="px-5 py-3">
                    <span className={`inline-flex items-center px-2 py-0.5 rounded-full text-xs font-medium ${k.is_active ? 'bg-green-50 text-green-700' : 'bg-gray-100 text-gray-500'}`}>
                      {k.is_active ? 'Active' : 'Inactive'}
                    </span>
                  </td>
                  <td className="px-5 py-3 text-sm text-gray-500">{k.last_used_at ? formatDate(k.last_used_at) : 'Never'}</td>
                  <td className="px-5 py-3 text-sm text-gray-500">{formatDate(k.created_at)}</td>
                  <td className="px-5 py-3">
                    {k.is_active && (
                      <button onClick={() => handleDeactivate(k.id)}
                        className="text-sm text-red-600 hover:underline">Deactivate</button>
                    )}
                  </td>
                </tr>
              ))}
              {keys.length === 0 && (
                <tr><td colSpan={6} className="px-5 py-8 text-center text-sm text-gray-400">No API keys yet</td></tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
