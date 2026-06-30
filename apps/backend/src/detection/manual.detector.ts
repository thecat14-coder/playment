import type { EvidenceSource } from './detector.interface.js';
import type { PaymentService } from '../services/payment.service.js';
import type { FastifyInstance } from 'fastify';

export class ManualDetector implements EvidenceSource {
  readonly name = 'manual';

  constructor(
    private paymentService: PaymentService,
    private internalApiKey: string,
  ) {}

  async start() {}

  async stop() {}

  registerRoutes(app: FastifyInstance) {
    app.post<{ Params: { id: string } }>(
      '/internal/payments/:id/mark-success',
      {
        preHandler: async (request, reply) => {
          if (request.headers['x-internal-key'] !== this.internalApiKey) {
            return reply.status(401).send({ error: { code: 'UNAUTHORIZED', message: 'Invalid internal key' } });
          }
        },
      },
      async (request, reply) => {
        const { id } = request.params;
        const body = request.body as { metadata?: Record<string, unknown> } | undefined;
        await this.paymentService.markSuccess(id, body?.metadata);
        return reply.status(200).send({ data: { payment_id: id, status: 'success' } });
      },
    );

    app.post<{ Params: { id: string } }>(
      '/internal/payments/:id/mark-failed',
      {
        preHandler: async (request, reply) => {
          if (request.headers['x-internal-key'] !== this.internalApiKey) {
            return reply.status(401).send({ error: { code: 'UNAUTHORIZED', message: 'Invalid internal key' } });
          }
        },
      },
      async (request, reply) => {
        const { id } = request.params;
        const body = request.body as { reason?: string } | undefined;
        await this.paymentService.markFailed(id, body?.reason ?? 'Manual failure');
        return reply.status(200).send({ data: { payment_id: id, status: 'failed' } });
      },
    );
  }
}
