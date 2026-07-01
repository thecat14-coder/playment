'use client';

import { useState } from 'react';
import { UPI_APPS } from '@gateway/shared';
import { buildAppLaunchUri, logUpiUri } from '@/lib/upi-uri';

const CHECKOUT_UPI_APPS = UPI_APPS.filter((app) =>
  ['PhonePe', 'Google Pay', 'Paytm', 'BHIM'].includes(app.name),
);

const APP_META: Record<string, { color: string; icon: string }> = {
  PhonePe: { color: '#5f259f', icon: '📱' },
  'Google Pay': { color: '#4285f4', icon: '💳' },
  Paytm: { color: '#00baf2', icon: '💰' },
  BHIM: { color: '#008c44', icon: '🏦' },
};

export function UpiButtons({ upiIntent, upiId }: { upiIntent: string; upiId: string }) {
  const [copiedUpiId, setCopiedUpiId] = useState(false);
  const [copiedUri, setCopiedUri] = useState(false);

  const handleAppClick = (appName: string, scheme: string) => {
    const launchUri = buildAppLaunchUri(upiIntent, scheme);
    logUpiUri(`launch ${appName}`, launchUri);
    window.location.href = launchUri;
  };

  const handleCopyUpiId = async () => {
    await navigator.clipboard.writeText(upiId);
    setCopiedUpiId(true);
    setTimeout(() => setCopiedUpiId(false), 2000);
  };

  const handleCopyUri = async () => {
    logUpiUri('copy', upiIntent);
    await navigator.clipboard.writeText(upiIntent);
    setCopiedUri(true);
    setTimeout(() => setCopiedUri(false), 2000);
  };

  return (
    <div className="space-y-3">
      <p className="text-sm text-gray-500 text-center">or pay using</p>

      <div className="grid grid-cols-2 gap-3 sm:grid-cols-4">
        {CHECKOUT_UPI_APPS.map((app) => {
          const meta = APP_META[app.name] ?? { color: '#374151', icon: '💳' };
          return (
            <button
              key={app.name}
              onClick={() => handleAppClick(app.name, app.scheme)}
              className="flex flex-col items-center gap-1.5 p-3 rounded-xl border border-gray-200 hover:border-gray-300 hover:bg-gray-50 transition-colors"
            >
              <span className="text-2xl">{meta.icon}</span>
              <span className="text-xs font-medium text-gray-700">{app.name}</span>
            </button>
          );
        })}
      </div>

      <div className="rounded-lg border border-amber-200 bg-amber-50 p-3 space-y-2">
        <p className="text-xs font-medium text-amber-900">Debug: Generated UPI URI</p>
        <p className="text-xs font-mono text-amber-950 break-all">{upiIntent}</p>
        <button
          onClick={handleCopyUri}
          className="w-full flex items-center justify-center gap-2 py-2 px-3 rounded-md border border-amber-300 bg-white text-xs text-amber-900 hover:bg-amber-100 transition-colors"
        >
          {copiedUri ? 'UPI URI copied!' : 'Copy UPI URI'}
        </button>
      </div>

      <button
        onClick={handleCopyUpiId}
        className="w-full flex items-center justify-center gap-2 py-2.5 px-4 rounded-lg border border-gray-200 text-sm text-gray-600 hover:bg-gray-50 transition-colors"
      >
        {copiedUpiId ? (
          <>
            <svg className="w-4 h-4 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" /></svg>
            Copied!
          </>
        ) : (
          <>
            <svg className="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" /></svg>
            Copy UPI ID: {upiId}
          </>
        )}
      </button>
    </div>
  );
}
