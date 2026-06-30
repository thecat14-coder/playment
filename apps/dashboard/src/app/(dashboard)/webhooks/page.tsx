'use client';

import { useEffect, useState } from 'react';
import { api } from '@/lib/api';
import { formatDate } from '@/components/format';

export default function WebhooksPage() {
  const [logs, setLogs] = useState<any[]>([]);
  const [testing, setTesting] = useState(false);
  const [testResult, setTestResult] = useState<string | null>(null);

  useEffect(() => { loadLogs(); }, []);

  const loadLogs = () => {
    api.webhooks.logs().then((r) => setLogs(r.data)).catch(() => {});
  };

  const handleTest = async () => {
    setTesting(true);
    setTestResult(null);
    try {
      await api.webhooks.test();
      setTestResult('Test webhook enqueued. Check logs below.');
      setTimeout(loadLogs, 2000);
    } catch (err: any) {
      setTestResult(err.message || 'Failed to send test');
    } finally {
      setTesting(false);
    }
  };

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-2xl font-bold">Webhooks</h1>
        <button onClick={handleTest} disabled={testing}
          className="px-4 py-2 bg-brand-600 text-white rounded-lg text-sm font-medium hover:bg-brand-700 disabled:opacity-50 transition-colors">
          {testing ? 'Sending...' : 'Send Test Webhook'}
        </button>
      </div>

      {testResult && (
        <div className="mb-4 p-3 rounded-lg bg-blue-50 text-blue-700 text-sm">{testResult}</div>
      )}

      <div className="bg-white rounded-xl border border-gray-200">
        <div className="px-5 py-4 border-b border-gray-100">
          <h2 className="font-semibold">Delivery Logs</h2>
        </div>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-100">
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Payment ID</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Status</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">HTTP</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Attempt</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Error</th>
                <th className="text-left text-xs font-medium text-gray-500 uppercase tracking-wider px-5 py-3">Time</th>
              </tr>
            </thead>
            <tbody>
              {logs.map((l: any) => (
                <tr key={l.id} className="border-b border-gray-50">
                  <td className="px-5 py-3 text-sm font-mono text-gray-500">{l.payment_id.slice(0, 12)}...</td>
                  <td className="px-5 py-3">
                    <span className={`inline-flex items-center px-2 py-0.5 rounded-full text-xs font-medium ${l.delivered ? 'bg-green-50 text-green-700' : 'bg-red-50 text-red-700'}`}>
                      {l.delivered ? 'Delivered' : 'Failed'}
                    </span>
                  </td>
                  <td className="px-5 py-3 text-sm font-mono">{l.response_status ?? '—'}</td>
                  <td className="px-5 py-3 text-sm text-center">{l.attempt}</td>
                  <td className="px-5 py-3 text-sm text-red-500 max-w-[200px] truncate">{l.error || '—'}</td>
                  <td className="px-5 py-3 text-sm text-gray-500">{formatDate(l.created_at)}</td>
                </tr>
              ))}
              {logs.length === 0 && (
                <tr><td colSpan={6} className="px-5 py-8 text-center text-sm text-gray-400">No webhook deliveries yet</td></tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
