'use client';

import { useEffect, useRef, useState, useCallback } from 'react';
import { getWebSocketUrl, getPollingUrl } from '@/lib/api';

export function usePaymentStatus(paymentId: string, initialStatus: string) {
  const [status, setStatus] = useState(initialStatus);
  const wsRef = useRef<WebSocket | null>(null);
  const pollRef = useRef<ReturnType<typeof setInterval> | null>(null);

  const startPolling = useCallback(() => {
    if (pollRef.current) return;
    pollRef.current = setInterval(async () => {
      try {
        const res = await fetch(getPollingUrl(paymentId));
        if (res.ok) {
          const json = await res.json();
          const newStatus = json.data.status;
          if (newStatus !== 'pending') {
            setStatus(newStatus);
            if (pollRef.current) {
              clearInterval(pollRef.current);
              pollRef.current = null;
            }
          }
        }
      } catch {}
    }, 5000);
  }, [paymentId]);

  useEffect(() => {
    if (initialStatus !== 'pending') return;

    const wsUrl = getWebSocketUrl(paymentId);

    try {
      const ws = new WebSocket(wsUrl);
      wsRef.current = ws;

      ws.onmessage = (event) => {
        const data = JSON.parse(event.data);
        if (data.type === 'status_update') {
          setStatus(data.status);
        }
      };

      ws.onclose = () => {
        startPolling();
      };

      ws.onerror = () => {
        ws.close();
      };
    } catch {
      startPolling();
    }

    return () => {
      wsRef.current?.close();
      if (pollRef.current) clearInterval(pollRef.current);
    };
  }, [paymentId, initialStatus, startPolling]);

  return status;
}
