'use client';

import { useEffect, useRef, useState, useCallback } from 'react';
import { getWebSocketUrl, getPollingUrl } from '@/lib/api';

export function usePaymentStatus(paymentId: string, initialStatus: string) {
  const [status, setStatus] = useState(initialStatus);
  const wsRef = useRef<WebSocket | null>(null);
  const pollRef = useRef<ReturnType<typeof setInterval> | null>(null);

  const pollOnce = useCallback(async () => {
    try {
      const res = await fetch(getPollingUrl(paymentId), { cache: 'no-store' });
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
    } catch {
      // retry on next interval
    }
  }, [paymentId]);

  const startPolling = useCallback(() => {
    if (pollRef.current) return;
    pollOnce();
    pollRef.current = setInterval(pollOnce, 3000);
  }, [pollOnce]);

  useEffect(() => {
    if (initialStatus !== 'pending') return;

    // Always poll — WebSocket is unreliable on some hosts (e.g. Railway)
    startPolling();

    const wsUrl = getWebSocketUrl(paymentId);

    try {
      const ws = new WebSocket(wsUrl);
      wsRef.current = ws;

      ws.onmessage = (event) => {
        const data = JSON.parse(event.data);
        if (data.type === 'status_update') {
          setStatus(data.status);
          if (pollRef.current) {
            clearInterval(pollRef.current);
            pollRef.current = null;
          }
        }
      };

      ws.onerror = () => {
        ws.close();
      };
    } catch {
      // polling already running
    }

    return () => {
      wsRef.current?.close();
      if (pollRef.current) clearInterval(pollRef.current);
    };
  }, [paymentId, initialStatus, startPolling]);

  return status;
}
