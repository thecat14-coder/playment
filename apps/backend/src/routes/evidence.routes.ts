import type { FastifyInstance } from 'fastify';
import { evidenceUploadSchema } from '@gateway/shared';
import type { EvidenceService } from '../services/evidence.service.js';
import type { MatchingService } from '../services/matching.service.js';
import type { Queue } from 'bullmq';
import { ValidationError } from '../utils/errors.js';

type IdParams = { id: string };

export function registerEvidenceRoutes(
  app: FastifyInstance,
  evidenceService: EvidenceService,
  matchingService: MatchingService,
  matchingQueue: Queue,
  deviceAuthMiddleware: (request: any, reply: any) => Promise<void>,
  jwtMiddleware: (request: any, reply: any) => Promise<void>,
) {
  app.post('/v1/evidence', { preHandler: deviceAuthMiddleware }, async (request, reply) => {
    const parsed = evidenceUploadSchema.safeParse(request.body);
    if (!parsed.success) {
      throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
        field: i.path.join('.'),
        issue: i.message,
      })));
    }

    const evidence = await evidenceService.uploadEvidence(
      request.deviceId!,
      request.merchantId!,
      parsed.data,
    );

    await matchingQueue.add('match-evidence', {
      evidenceId: evidence.id,
    });

    return reply.status(201).send({ data: evidence });
  });

  app.get('/v1/evidence', { preHandler: jwtMiddleware }, async (request, reply) => {
    const result = await evidenceService.listEvidence(request.merchantId!, {
      page: Number((request.query as any).page) || 1,
      limit: Number((request.query as any).limit) || 20,
      matched: (request.query as any).matched !== undefined
        ? (request.query as any).matched === 'true'
        : undefined,
      from: (request.query as any).from ? new Date((request.query as any).from) : undefined,
      to: (request.query as any).to ? new Date((request.query as any).to) : undefined,
    });
    return reply.send(result);
  });

  app.get<{ Params: IdParams }>('/v1/evidence/:id', { preHandler: jwtMiddleware }, async (request, reply) => {
    const evidence = await evidenceService.getEvidence(request.params.id);
    if (!evidence) {
      return reply.status(404).send({
        error: { code: 'NOT_FOUND', message: 'Evidence not found' },
      });
    }
    return reply.send({ data: evidence });
  });

  app.get<{ Params: IdParams }>('/v1/payments/:id/evidence', { preHandler: jwtMiddleware }, async (request, reply) => {
    const evidence = await evidenceService.getEvidenceForPayment(request.params.id);
    return reply.send({ data: evidence });
  });

  app.get<{ Params: IdParams }>('/v1/payments/:id/matching', { preHandler: jwtMiddleware }, async (request, reply) => {
    const matching = await matchingService.getMatchingForPayment(request.params.id);
    return reply.send({ data: matching });
  });
}
