import type { FastifyInstance } from 'fastify';
import { updateAccountSchema } from '@gateway/shared';
import type { MerchantService } from '../services/merchant.service.js';
import { ValidationError } from '../utils/errors.js';

export function registerAccountRoutes(
  app: FastifyInstance,
  merchantService: MerchantService,
  authMiddleware: (request: any, reply: any) => Promise<void>,
) {
  app.get(
    '/v1/account',
    { preHandler: authMiddleware },
    async (request, reply) => {
      const account = await merchantService.getAccount(request.merchantId!);
      return reply.send({ data: account });
    },
  );

  app.patch(
    '/v1/account',
    { preHandler: authMiddleware },
    async (request, reply) => {
      const parsed = updateAccountSchema.safeParse(request.body);
      if (!parsed.success) {
        throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
          field: i.path.join('.'),
          issue: i.message,
        })));
      }

      const account = await merchantService.updateAccount(request.merchantId!, parsed.data);
      return reply.send({ data: account });
    },
  );
}
