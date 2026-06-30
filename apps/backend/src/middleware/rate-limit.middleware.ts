import type { FastifyInstance } from 'fastify';
import rateLimit from '@fastify/rate-limit';
import type Redis from 'ioredis';

export async function registerRateLimit(app: FastifyInstance, redis: Redis) {
  await app.register(rateLimit, {
    max: 100,
    timeWindow: '1 minute',
    redis,
    keyGenerator: (request) => {
      return request.merchantId ?? request.ip;
    },
  });
}
