'use client';

import { useEffect, useState } from 'react';
import { adminApi } from '@/lib/api';

export default function HealthPage() {
  const [healthData, setHealthData] = useState<any>(null);

  useEffect(() => {
    adminApi.health.overview().then((r) => setHealthData(r.data)).catch(() => {});
  }, []);

  const merchants = Array.isArray(healthData) ? healthData : [];

  return (
    <div className="p-8">
      <h1 className="text-2xl font-bold mb-6">System Health</h1>
      <div className="grid gap-4">
        {merchants.map((m: any) => (
          <div key={m.merchant_id} className="bg-white rounded-lg shadow p-4">
            <div className="flex justify-between items-center">
              <div>
                <h3 className="font-semibold">{m.merchant_name}</h3>
                <p className="text-sm text-gray-500 font-mono">{m.merchant_id}</p>
              </div>
              <div className="text-right">
                <p className={`text-xl font-bold ${m.health_score >= 70 ? 'text-green-600' : m.health_score >= 40 ? 'text-amber-600' : 'text-red-600'}`}>
                  {m.health_score}
                </p>
                <p className="text-sm text-gray-500">
                  {m.online_devices}/{m.total_devices} devices online
                </p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
