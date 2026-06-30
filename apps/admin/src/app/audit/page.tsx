'use client';

import { useEffect, useState } from 'react';
import { adminApi } from '@/lib/api';

export default function AuditPage() {
  const [logs, setLogs] = useState<any[]>([]);

  useEffect(() => {
    adminApi.audit.list().then((r) => setLogs(r.data)).catch(() => {});
  }, []);

  return (
    <div className="p-8">
      <h1 className="text-2xl font-bold mb-6">Audit Logs</h1>
      <table className="w-full bg-white rounded-lg shadow">
        <thead className="bg-gray-100">
          <tr>
            <th className="p-3 text-left">Action</th>
            <th className="p-3 text-left">Actor</th>
            <th className="p-3 text-left">Resource</th>
            <th className="p-3 text-left">Details</th>
            <th className="p-3 text-left">Timestamp</th>
          </tr>
        </thead>
        <tbody>
          {logs.map((log) => (
            <tr key={log.id} className="border-t">
              <td className="p-3">{log.action}</td>
              <td className="p-3">{log.actor_type}:{log.actor_id?.slice(0, 8)}</td>
              <td className="p-3">{log.resource_type}:{log.resource_id?.slice(0, 8)}</td>
              <td className="p-3 text-sm text-gray-600">{JSON.stringify(log.details)}</td>
              <td className="p-3">{new Date(log.created_at).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
