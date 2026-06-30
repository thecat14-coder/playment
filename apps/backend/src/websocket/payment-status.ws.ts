import type { FastifyInstance } from 'fastify';
import type { WebSocket } from '@fastify/websocket';
import type { PaymentService } from '../services/payment.service.js';

const paymentSubscribers = new Map<string, Set<WebSocket>>();

export function registerWebSocket(app: FastifyInstance, paymentService: PaymentService) {
  paymentService.onStatusChange((paymentId, status) => {
    const subs = paymentSubscribers.get(paymentId);
    if (!subs) return;

    const message = JSON.stringify({ type: 'status_update', payment_id: paymentId, status });
    for (const ws of subs) {
      if (ws.readyState === ws.OPEN) {
        ws.send(message);
      }
    }

    if (status !== 'pending') {
      for (const ws of subs) {
        ws.close(1000, 'Payment resolved');
      }
      paymentSubscribers.delete(paymentId);
    }
  });

  app.get<{ Params: { paymentId: string } }>(
    '/ws/payments/:paymentId',
    { websocket: true },
    (socket, request) => {
      const { paymentId } = request.params;

      if (!paymentSubscribers.has(paymentId)) {
        paymentSubscribers.set(paymentId, new Set());
      }
      paymentSubscribers.get(paymentId)!.add(socket);

      socket.on('close', () => {
        const subs = paymentSubscribers.get(paymentId);
        if (subs) {
          subs.delete(socket);
          if (subs.size === 0) paymentSubscribers.delete(paymentId);
        }
      });
    },
  );
}
