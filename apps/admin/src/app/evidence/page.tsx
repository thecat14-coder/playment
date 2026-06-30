'use client';

import { useEffect, useState } from 'react';
import { adminApi } from '@/lib/api';

export default function EvidencePage() {
  const [evidence, setEvidence] = useState<any[]>([]);

  useEffect(() => {
    adminApi.evidence.list().then((r) => setEvidence(r.data)).catch(() => {});
  }, []);

  return (
    <div className="p-8">
      <h1 className="text-2xl font-bold mb-6">Evidence Records</h1>
      <table className="w-full bg-white rounded-lg shadow">
        <thead className="bg-gray-100">
          <tr>
            <th className="p-3 text-left">ID</th>
            <th className="p-3 text-left">Amount</th>
            <th className="p-3 text-left">UTR</th>
            <th className="p-3 text-left">UPI App</th>
            <th className="p-3 text-left">Matched</th>
            <th className="p-3 text-left">Uploaded</th>
          </tr>
        </thead>
        <tbody>
          {evidence.map((e) => (
            <tr key={e.id} className="border-t">
              <td className="p-3 font-mono text-sm">{e.id}</td>
              <td className="p-3">₹{(e.amount / 100).toFixed(2)}</td>
              <td className="p-3 font-mono text-sm">{e.utr || '-'}</td>
              <td className="p-3">{e.upi_app}</td>
              <td className="p-3">
                <span className={`px-2 py-1 rounded text-sm ${e.matched ? 'bg-green-100 text-green-800' : 'bg-gray-100 text-gray-800'}`}>
                  {e.matched ? 'Yes' : 'No'}
                </span>
              </td>
              <td className="p-3">{new Date(e.uploaded_at).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
