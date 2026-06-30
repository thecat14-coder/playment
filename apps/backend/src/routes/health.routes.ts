import type { FastifyInstance } from 'fastify';
import type { HealthService } from '../services/health.service.js';

export function registerHealthRoutes(
  app: FastifyInstance,
  healthService: HealthService,
  jwtMiddleware: (request: any, reply: any) => Promise<void>,
) {
  app.get('/v1/health/summary', { preHandler: jwtMiddleware }, async (request, reply) => {
    const summary = await healthService.getMerchantHealthSummary(request.merchantId!);
    return reply.send({ data: summary });
  });

  app.get('/v1/health/devices', { preHandler: jwtMiddleware }, async (request, reply) => {
    const merchantId = request.merchantId!;
    const devices = (await healthService.getMerchantHealthSummary(merchantId)).devices;
    return reply.send({ data: devices });
  });
}
