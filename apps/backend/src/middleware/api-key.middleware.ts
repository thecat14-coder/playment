import type { FastifyRequest, FastifyReply } from 'fastify';
import type { AuthService } from '../services/auth.service.js';
import type { ApiKeyRepository } from '../repositories/api-key.repository.js';
import { UnauthorizedError } from '../utils/errors.js';

export function createApiKeyMiddleware(
  authService: AuthService,
  apiKeyRepo: ApiKeyRepository,
) {
  return async (request: FastifyRequest, reply: FastifyReply) => {
    const apiKey = request.headers['x-api-key'] as string | undefined;
    if (!apiKey) {
      throw new UnauthorizedError('Missing X-Api-Key header');
    }

    const hash = authService.hashApiKey(apiKey);
    const record = await apiKeyRepo.findByHash(hash);
    if (!record) {
      throw new UnauthorizedError('Invalid API key');
    }

    request.merchantId = record.merchant_id;

    apiKeyRepo.updateLastUsed(record.id).catch(() => {});
  };
}
