'use client';

import { useState } from 'react';

const UPI_APPS = [
  { name: 'PhonePe', scheme: 'phonepe', color: '#5f259f', icon: '📱' },
  { name: 'Google Pay', scheme: 'tez://upi', color: '#4285f4', icon: '💳' },
  { name: 'Paytm', scheme: 'paytmmp', color: '#00baf2', icon: '💰' },
];

export function UpiButtons({ upiIntent, upiId }: { upiIntent: string; upiId: string }) {
  const [copied, setCopied] = useState(false);

  const handleAppClick = (scheme: string) => {
    const intentUrl = upiIntent.replace('upi://', `${scheme}://`);
    window.location.href = intentUrl;
  };

  const handleCopyUpiId = async () => {
    await navigator.clipboard.writeText(upiId);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  return (
    <div className="space-y-3">
      <p className="text-sm text-gray-500 text-center">or pay using</p>

      <div className="grid grid-cols-3 gap-3">
        {UPI_APPS.map((app) => (
          <button
            key={app.name}
            onClick={() => handleAppClick(app.scheme)}
            className="flex flex-col items-center gap-1.5 p-3 rounded-xl border border-gray-200 hover:border-gray-300 hover:bg-gray-50 transition-colors"
          >
            <span className="text-2xl">{app.icon}</span>
            <span className="text-xs font-medium text-gray-700">{app.name}</span>
          </button>
        ))}
      </div>

      <button
        onClick={handleCopyUpiId}
        className="w-full flex items-center justify-center gap-2 py-2.5 px-4 rounded-lg border border-gray-200 text-sm text-gray-600 hover:bg-gray-50 transition-colors"
      >
        {copied ? (
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
