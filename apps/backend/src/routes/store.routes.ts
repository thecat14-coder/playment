import type { FastifyInstance } from 'fastify';
import { createStoreSchema, updateStoreSchema, createCounterSchema } from '@gateway/shared';
import type { StoreService } from '../services/store.service.js';
import { ValidationError } from '../utils/errors.js';

type IdParams = { id: string };

export function registerStoreRoutes(
  app: FastifyInstance,
  storeService: StoreService,
  jwtMiddleware: (request: any, reply: any) => Promise<void>,
) {
  app.post('/v1/stores', { preHandler: jwtMiddleware }, async (request, reply) => {
    const parsed = createStoreSchema.safeParse(request.body);
    if (!parsed.success) {
      throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
        field: i.path.join('.'),
        issue: i.message,
      })));
    }

    const store = await storeService.createStore(request.merchantId!, parsed.data);
    return reply.status(201).send({ data: store });
  });

  app.get('/v1/stores', { preHandler: jwtMiddleware }, async (request, reply) => {
    const stores = await storeService.listStores(request.merchantId!);
    return reply.send({ data: stores });
  });

  app.patch<{ Params: IdParams }>('/v1/stores/:id', { preHandler: jwtMiddleware }, async (request, reply) => {
    const parsed = updateStoreSchema.safeParse(request.body);
    if (!parsed.success) {
      throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
        field: i.path.join('.'),
        issue: i.message,
      })));
    }

    const store = await storeService.updateStore(request.params.id, request.merchantId!, parsed.data);
    return reply.send({ data: store });
  });

  app.delete<{ Params: IdParams }>('/v1/stores/:id', { preHandler: jwtMiddleware }, async (request, reply) => {
    await storeService.deleteStore(request.params.id, request.merchantId!);
    return reply.status(204).send();
  });

  app.post<{ Params: IdParams }>('/v1/stores/:id/counters', { preHandler: jwtMiddleware }, async (request, reply) => {
    const parsed = createCounterSchema.safeParse(request.body);
    if (!parsed.success) {
      throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
        field: i.path.join('.'),
        issue: i.message,
      })));
    }

    const counter = await storeService.createCounter(request.params.id, request.merchantId!, parsed.data);
    return reply.status(201).send({ data: counter });
  });

  app.get<{ Params: IdParams }>('/v1/stores/:id/counters', { preHandler: jwtMiddleware }, async (request, reply) => {
    const counters = await storeService.listCounters(request.params.id, request.merchantId!);
    return reply.send({ data: counters });
  });
}
