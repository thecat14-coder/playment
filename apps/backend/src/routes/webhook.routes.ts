import type { FastifyInstance } from 'fastify';
import { paginationSchema } from '@gateway/shared';
import type { WebhookService } from '../services/webhook.service.js';
import type { MerchantRepository } from '../repositories/merchant.repository.js';
import type { WebhookLogRepository } from '../repositories/webhook-log.repository.js';
import { ValidationError, AppError } from '../utils/errors.js';

export function registerWebhookRoutes(
  app: FastifyInstance,
  webhookService: WebhookService,
  merchantRepo: MerchantRepository,
  webhookLogRepo: WebhookLogRepository,
  authMiddleware: (request: any, reply: any) => Promise<void>,
) {
  app.post(
    '/v1/webhooks/test',
    { preHandler: authMiddleware },
    async (request, reply) => {
      const merchant = await merchantRepo.findById(request.merchantId!);
      if (!merchant?.webhook_url) {
        throw new AppError(400, 'NO_WEBHOOK_URL', 'No webhook URL configured');
      }

      await webhookService.enqueueTest({
        merchantId: merchant.id,
        webhookUrl: merchant.webhook_url,
        webhookSecret: merchant.webhook_secret,
      });

      return reply.send({ data: { message: 'Test webhook enqueued' } });
    },
  );

  app.get(
    '/v1/webhooks/logs',
    { preHandler: authMiddleware },
    async (request, reply) => {
      const parsed = paginationSchema.safeParse(request.query);
      if (!parsed.success) {
        throw new ValidationError('Invalid query parameters', parsed.error.issues.map((i) => ({
          field: i.path.join('.'),
          issue: i.message,
        })));
      }

      const result = await webhookLogRepo.listByMerchant(request.merchantId!, parsed.data);
      return reply.send(result);
    },
  );
}
