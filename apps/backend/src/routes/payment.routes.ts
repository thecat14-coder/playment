import type { FastifyInstance } from 'fastify';
import { paymentListFilterSchema } from '@gateway/shared';
import type { PaymentService } from '../services/payment.service.js';
import { ValidationError } from '../utils/errors.js';

export function registerPaymentRoutes(
  app: FastifyInstance,
  paymentService: PaymentService,
  authMiddleware: (request: any, reply: any) => Promise<void>,
) {
  app.get<{ Params: { id: string } }>(
    '/v1/payments/:id',
    { preHandler: authMiddleware },
    async (request, reply) => {
      const payment = await paymentService.getPaymentForMerchant(request.merchantId!, request.params.id);
      return reply.send({ data: payment });
    },
  );

  app.get(
    '/v1/payments',
    { preHandler: authMiddleware },
    async (request, reply) => {
      const parsed = paymentListFilterSchema.safeParse(request.query);
      if (!parsed.success) {
        throw new ValidationError('Invalid query parameters', parsed.error.issues.map((i) => ({
          field: i.path.join('.'),
          issue: i.message,
        })));
      }

      const result = await paymentService.listPayments(request.merchantId!, parsed.data);
      return reply.send(result);
    },
  );
}
