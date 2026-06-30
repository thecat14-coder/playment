import type { FastifyInstance } from 'fastify';
import type { MerchantRepository } from '../repositories/merchant.repository.js';
import type { DeviceRepository } from '../repositories/device.repository.js';
import type { EvidenceRepository } from '../repositories/evidence.repository.js';
import type { ReviewService } from '../services/review.service.js';
import type { MatchingService } from '../services/matching.service.js';
import type { DeviceService } from '../services/device.service.js';
import type { PaymentRepository } from '../repositories/payment.repository.js';
import type { AuditLogRepository } from '../repositories/audit-log.repository.js';

type IdParams = { id: string };
type PayIdParams = { paymentId: string };

export function registerAdminRoutes(
  app: FastifyInstance,
  merchantRepo: MerchantRepository,
  deviceRepo: DeviceRepository,
  evidenceRepo: EvidenceRepository,
  reviewService: ReviewService,
  matchingService: MatchingService,
  deviceService: DeviceService,
  paymentRepo: PaymentRepository,
  auditLogRepo: AuditLogRepository,
  adminJwtMiddleware: (request: any, reply: any) => Promise<void>,
) {
  app.get('/admin/merchants', { preHandler: adminJwtMiddleware }, async (request, reply) => {
    const merchants = await merchantRepo.findAll();
    return reply.send({ data: merchants });
  });

  app.post<{ Params: IdParams }>('/admin/merchants/:id/suspend', { preHandler: adminJwtMiddleware }, async (request, reply) => {
    const merchant = await merchantRepo.findById(request.params.id);
    if (!merchant) {
      return reply.status(404).send({
        error: { code: 'NOT_FOUND', message: 'Merchant not found' },
      });
    }
    await merchantRepo.update(request.params.id, { status: 'suspended' } as any);
    return reply.send({ data: { status: 'suspended' } });
  });

  app.get('/admin/devices', { preHandler: adminJwtMiddleware }, async (request, reply) => {
    const result = await deviceRepo.findAll({
      page: Number((request.query as any).page) || 1,
      limit: Number((request.query as any).limit) || 20,
      status: (request.query as any).status,
      merchant_id: (request.query as any).merchant_id,
    });
    return reply.send(result);
  });

  app.post<{ Params: IdParams }>('/admin/devices/:id/disable', { preHandler: adminJwtMiddleware }, async (request, reply) => {
    const device = await deviceRepo.findById(request.params.id);
    if (!device) {
      return reply.status(404).send({
        error: { code: 'NOT_FOUND', message: 'Device not found' },
      });
    }
    await deviceRepo.updateStatus(request.params.id, 'suspended');
    await deviceService.setOnlineStatus(request.params.id, false);
    return reply.send({ data: { status: 'suspended' } });
  });

  app.get('/admin/evidence', { preHandler: adminJwtMiddleware }, async (request, reply) => {
    const result = await evidenceRepo.findAll({
      page: Number((request.query as any).page) || 1,
      limit: Number((request.query as any).limit) || 20,
      merchant_id: (request.query as any).merchant_id,
      matched: (request.query as any).matched !== undefined
        ? (request.query as any).matched === 'true'
        : undefined,
    });
    return reply.send(result);
  });

  app.get('/admin/reviews', { preHandler: adminJwtMiddleware }, async (request, reply) => {
    const result = await reviewService.listAllPendingReviews({
      page: Number((request.query as any).page) || 1,
      limit: Number((request.query as any).limit) || 20,
    });
    return reply.send(result);
  });

  app.post<{ Params: IdParams }>('/admin/reviews/:id/approve', { preHandler: adminJwtMiddleware }, async (request, reply) => {
    const body = request.body as { reason?: string } | undefined;
    await reviewService.approveReview(request.params.id, request.merchantId!, 'admin', body?.reason);
    return reply.send({ data: { status: 'approved' } });
  });

  app.post<{ Params: IdParams }>('/admin/reviews/:id/reject', { preHandler: adminJwtMiddleware }, async (request, reply) => {
    const body = request.body as { reason?: string } | undefined;
    await reviewService.rejectReview(request.params.id, request.merchantId!, 'admin', body?.reason);
    return reply.send({ data: { status: 'rejected' } });
  });

  app.post<{ Params: IdParams }>('/admin/reviews/:id/force-fail', { preHandler: adminJwtMiddleware }, async (request, reply) => {
    const body = request.body as { reason?: string } | undefined;
    await reviewService.forceFailReview(request.params.id, request.merchantId!, body?.reason);
    return reply.send({ data: { status: 'force_failed' } });
  });

  app.post<{ Params: PayIdParams }>('/admin/matching/retry/:paymentId', { preHandler: adminJwtMiddleware }, async (request, reply) => {
    const result = await matchingService.retryMatch(request.params.paymentId);
    if (!result) {
      return reply.status(404).send({
        error: { code: 'NOT_FOUND', message: 'Payment not found or no evidence available' },
      });
    }
    return reply.send({ data: result });
  });

  app.get('/admin/health', { preHandler: adminJwtMiddleware }, async (request, reply) => {
    const merchantId = (request.query as any).merchant_id;
    if (merchantId) {
      const merchant = await merchantRepo.findById(merchantId);
      if (!merchant) {
        return reply.status(404).send({
          error: { code: 'NOT_FOUND', message: 'Merchant not found' },
        });
      }
      const devices = await deviceRepo.findByMerchantId(merchantId);
      const activeDevices = devices.filter((d) => d.status === 'active');
      const onlineDevices = activeDevices.filter((d) => d.is_online);
      return reply.send({
        data: {
          merchant_id: merchantId,
          total_devices: devices.length,
          active_devices: activeDevices.length,
          online_devices: onlineDevices.length,
          devices: activeDevices.map((d) => ({
            id: d.id,
            model: d.model,
            health_score: d.health_score,
            is_online: d.is_online,
            status: d.status,
          })),
        },
      });
    }

    const allMerchants = await merchantRepo.findAll();
    const merchantHealthSummaries = await Promise.all(
      allMerchants.map(async (m) => {
        const devices = await deviceRepo.findByMerchantId(m.id);
        const activeDevices = devices.filter((d) => d.status === 'active');
        const onlineDevices = activeDevices.filter((d) => d.is_online);
        return {
          merchant_id: m.id,
          merchant_name: m.name,
          health_score: m.health_score,
          total_devices: activeDevices.length,
          online_devices: onlineDevices.length,
        };
      }),
    );

    return reply.send({ data: merchantHealthSummaries });
  });

  app.get('/admin/audit-logs', { preHandler: adminJwtMiddleware }, async (request, reply) => {
    const result = await auditLogRepo.findAll({
      page: Number((request.query as any).page) || 1,
      limit: Number((request.query as any).limit) || 50,
      actor_type: (request.query as any).actor_type,
      resource_type: (request.query as any).resource_type,
      resource_id: (request.query as any).resource_id,
    });
    return reply.send(result);
  });
}
