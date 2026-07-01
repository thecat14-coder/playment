'use client';

import { useEffect } from 'react';
import { logUpiUri } from '@/lib/upi-uri';

export function QrDisplay({
  qrUrl,
  merchantName,
  upiIntent,
}: {
  qrUrl: string;
  merchantName: string;
  upiIntent: string;
}) {
  useEffect(() => {
    logUpiUri('QR encodes', upiIntent);
  }, [upiIntent]);

  return (
    <div className="flex flex-col items-center">
      <p className="text-sm text-gray-500 mb-3">Scan with any UPI app</p>
      <div className="bg-white p-4 rounded-xl shadow-sm border border-gray-100">
        <img
          src={qrUrl}
          alt={`QR code to pay ${merchantName}`}
          width={240}
          height={240}
          className="rounded-lg"
        />
      </div>
    </div>
  );
}
