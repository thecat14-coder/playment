import type { FastifyInstance } from 'fastify';
import { createPaymentLinkSchema, paymentListFilterSchema } from '@gateway/shared';
import type { PaymentService } from '../services/payment.service.js';
import { ValidationError } from '../utils/errors.js';

export function registerPaymentLinkRoutes(
  app: FastifyInstance,
  paymentService: PaymentService,
  authMiddleware: (request: any, reply: any) => Promise<void>,
) {
  app.post(
    '/v1/payment-links',
    { preHandler: authMiddleware },
    async (request, reply) => {
      const parsed = createPaymentLinkSchema.safeParse(request.body);
      if (!parsed.success) {
        throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
          field: i.path.join('.'),
          issue: i.message,
        })));
      }

      const result = await paymentService.createPaymentLink(request.merchantId!, parsed.data);
      return reply.status(201).send({ data: result });
    },
  );

  app.get<{ Params: { id: string } }>(
    '/v1/payment-links/:id',
    { preHandler: authMiddleware },
    async (request, reply) => {
      const payment = await paymentService.getPaymentForMerchant(request.merchantId!, request.params.id);
      return reply.send({ data: payment });
    },
  );

  app.get(
    '/v1/payment-links',
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
