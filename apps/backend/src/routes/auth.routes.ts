import type { FastifyInstance } from 'fastify';
import { registerMerchantSchema, loginSchema } from '@gateway/shared';
import type { MerchantService } from '../services/merchant.service.js';
import { ValidationError } from '../utils/errors.js';

export function registerAuthRoutes(app: FastifyInstance, merchantService: MerchantService) {
  app.post('/v1/auth/register', async (request, reply) => {
    const parsed = registerMerchantSchema.safeParse(request.body);
    if (!parsed.success) {
      throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
        field: i.path.join('.'),
        issue: i.message,
      })));
    }

    const result = await merchantService.register(parsed.data);
    return reply.status(201).send({ data: result });
  });

  app.post('/v1/auth/login', async (request, reply) => {
    const parsed = loginSchema.safeParse(request.body);
    if (!parsed.success) {
      throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
        field: i.path.join('.'),
        issue: i.message,
      })));
    }

    const result = await merchantService.login(parsed.data.email, parsed.data.password);
    return reply.send({ data: result });
  });

  app.post('/v1/auth/refresh', async (request, reply) => {
    const body = request.body as { refresh_token?: string };
    if (!body?.refresh_token) {
      throw new ValidationError('refresh_token is required');
    }

    const result = await merchantService.refresh(body.refresh_token);
    return reply.send({ data: result });
  });
}
