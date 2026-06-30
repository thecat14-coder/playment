import type { FastifyInstance } from 'fastify';
import { reviewActionSchema } from '@gateway/shared';
import type { ReviewService } from '../services/review.service.js';
import { ValidationError } from '../utils/errors.js';

type IdParams = { id: string };

export function registerReviewRoutes(
  app: FastifyInstance,
  reviewService: ReviewService,
  jwtMiddleware: (request: any, reply: any) => Promise<void>,
) {
  app.get('/v1/reviews', { preHandler: jwtMiddleware }, async (request, reply) => {
    const result = await reviewService.listPendingReviews(request.merchantId!, {
      page: Number((request.query as any).page) || 1,
      limit: Number((request.query as any).limit) || 20,
    });
    return reply.send(result);
  });

  app.get<{ Params: IdParams }>('/v1/reviews/:id', { preHandler: jwtMiddleware }, async (request, reply) => {
    const review = await reviewService.getReview(request.params.id);
    if (!review) {
      return reply.status(404).send({
        error: { code: 'NOT_FOUND', message: 'Review not found' },
      });
    }
    return reply.send({ data: review });
  });

  app.post<{ Params: IdParams }>('/v1/reviews/:id/approve', { preHandler: jwtMiddleware }, async (request, reply) => {
    const parsed = reviewActionSchema.safeParse(request.body);
    const reason = parsed.success ? parsed.data.reason : undefined;

    await reviewService.approveReview(request.params.id, request.merchantId!, 'merchant', reason);
    return reply.send({ data: { status: 'approved' } });
  });

  app.post<{ Params: IdParams }>('/v1/reviews/:id/reject', { preHandler: jwtMiddleware }, async (request, reply) => {
    const parsed = reviewActionSchema.safeParse(request.body);
    const reason = parsed.success ? parsed.data.reason : undefined;

    await reviewService.rejectReview(request.params.id, request.merchantId!, 'merchant', reason);
    return reply.send({ data: { status: 'rejected' } });
  });
}
