import type { FastifyInstance } from 'fastify';
import { createApiKeySchema, updateApiKeySchema } from '@gateway/shared';
import type { AuthService } from '../services/auth.service.js';
import type { ApiKeyRepository } from '../repositories/api-key.repository.js';
import { ValidationError, NotFoundError } from '../utils/errors.js';
import { generateId } from '../utils/ulid.js';

export function registerApiKeyRoutes(
  app: FastifyInstance,
  authService: AuthService,
  apiKeyRepo: ApiKeyRepository,
  authMiddleware: (request: any, reply: any) => Promise<void>,
) {
  app.post(
    '/v1/api-keys',
    { preHandler: authMiddleware },
    async (request, reply) => {
      const parsed = createApiKeySchema.safeParse(request.body ?? {});
      if (!parsed.success) {
        throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
          field: i.path.join('.'),
          issue: i.message,
        })));
      }

      const { key, hash, prefix } = authService.generateApiKey();

      await apiKeyRepo.create({
        id: generateId(),
        merchant_id: request.merchantId!,
        key_hash: hash,
        key_prefix: prefix,
        label: parsed.data.label ?? null,
      });

      return reply.status(201).send({
        data: { key, prefix, label: parsed.data.label ?? null },
      });
    },
  );

  app.get(
    '/v1/api-keys',
    { preHandler: authMiddleware },
    async (request, reply) => {
      const keys = await apiKeyRepo.listByMerchant(request.merchantId!);
      return reply.send({ data: keys });
    },
  );

  app.patch<{ Params: { id: string } }>(
    '/v1/api-keys/:id',
    { preHandler: authMiddleware },
    async (request, reply) => {
      const parsed = updateApiKeySchema.safeParse(request.body);
      if (!parsed.success) {
        throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
          field: i.path.join('.'),
          issue: i.message,
        })));
      }

      const updated = await apiKeyRepo.update(request.params.id, request.merchantId!, parsed.data);
      if (!updated) throw new NotFoundError('API key not found');

      return reply.send({ data: updated });
    },
  );
}
