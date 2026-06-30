import type { FastifyInstance, FastifyRequest } from 'fastify';
import {
  deviceRegisterSchema,
  deviceHeartbeatSchema,
  deviceStatusSchema,
  deviceAssignSchema,
  healthReportSchema,
} from '@gateway/shared';
import type { DeviceService } from '../services/device.service.js';
import type { HealthService } from '../services/health.service.js';
import { ValidationError, NotFoundError } from '../utils/errors.js';

type IdParams = { id: string };

export function registerDeviceRoutes(
  app: FastifyInstance,
  deviceService: DeviceService,
  healthService: HealthService,
  deviceAuthMiddleware: (request: any, reply: any) => Promise<void>,
  jwtMiddleware: (request: any, reply: any) => Promise<void>,
) {
  app.post('/v1/devices/register', { preHandler: jwtMiddleware }, async (request, reply) => {
    const parsed = deviceRegisterSchema.safeParse(request.body);
    if (!parsed.success) {
      throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
        field: i.path.join('.'),
        issue: i.message,
      })));
    }

    const result = await deviceService.register(request.merchantId!, parsed.data);
    return reply.status(201).send({ data: result });
  });

  app.post<{ Params: IdParams }>('/v1/devices/:id/heartbeat', { preHandler: deviceAuthMiddleware }, async (request, reply) => {
    const parsed = deviceHeartbeatSchema.safeParse(request.body);
    if (!parsed.success) {
      throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
        field: i.path.join('.'),
        issue: i.message,
      })));
    }

    if (request.params.id !== request.deviceId) {
      throw new ValidationError('Device ID mismatch');
    }

    await deviceService.handleHeartbeat(request.params.id, parsed.data.listener_running);
    return reply.send({ data: { status: 'ok' } });
  });

  app.post<{ Params: IdParams }>('/v1/devices/:id/health', { preHandler: deviceAuthMiddleware }, async (request, reply) => {
    const parsed = healthReportSchema.safeParse(request.body);
    if (!parsed.success) {
      throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
        field: i.path.join('.'),
        issue: i.message,
      })));
    }

    if (request.params.id !== request.deviceId) {
      throw new ValidationError('Device ID mismatch');
    }

    const healthScore = await deviceService.handleHealthReport(request.params.id, parsed.data);
    return reply.send({ data: { health_score: healthScore } });
  });

  app.patch<{ Params: IdParams }>('/v1/devices/:id/status', { preHandler: deviceAuthMiddleware }, async (request, reply) => {
    const parsed = deviceStatusSchema.safeParse(request.body);
    if (!parsed.success) {
      throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
        field: i.path.join('.'),
        issue: i.message,
      })));
    }

    if (request.params.id !== request.deviceId) {
      throw new ValidationError('Device ID mismatch');
    }

    await deviceService.setOnlineStatus(request.params.id, parsed.data.is_online);
    return reply.send({ data: { status: 'ok' } });
  });

  app.get('/v1/devices', { preHandler: jwtMiddleware }, async (request, reply) => {
    const result = await deviceService.listDevices(request.merchantId!, {
      page: Number((request.query as any).page) || 1,
      limit: Number((request.query as any).limit) || 20,
      status: (request.query as any).status,
    });
    return reply.send(result);
  });

  app.get<{ Params: IdParams }>('/v1/devices/:id', { preHandler: jwtMiddleware }, async (request, reply) => {
    const device = await deviceService.getDevice(request.params.id);
    if (!device || device.merchant_id !== request.merchantId) {
      throw new NotFoundError('Device not found');
    }
    return reply.send({ data: device });
  });

  app.patch<{ Params: IdParams }>('/v1/devices/:id/assign', { preHandler: jwtMiddleware }, async (request, reply) => {
    const parsed = deviceAssignSchema.safeParse(request.body);
    if (!parsed.success) {
      throw new ValidationError('Validation failed', parsed.error.issues.map((i) => ({
        field: i.path.join('.'),
        issue: i.message,
      })));
    }

    const device = await deviceService.getDevice(request.params.id);
    if (!device || device.merchant_id !== request.merchantId) {
      throw new NotFoundError('Device not found');
    }

    const updated = await deviceService.assignDevice(
      request.params.id,
      parsed.data.store_id,
      parsed.data.counter_id,
    );
    return reply.send({ data: updated });
  });

  app.delete<{ Params: IdParams }>('/v1/devices/:id', { preHandler: jwtMiddleware }, async (request, reply) => {
    const device = await deviceService.getDevice(request.params.id);
    if (!device || device.merchant_id !== request.merchantId) {
      throw new NotFoundError('Device not found');
    }

    await deviceService.deregister(request.params.id);
    return reply.status(204).send();
  });
}
