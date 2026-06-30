import type { FastifyRequest, FastifyReply } from 'fastify';
import type { AuthService } from '../services/auth.service.js';
import type { DeviceRepository } from '../repositories/device.repository.js';
import { UnauthorizedError, ForbiddenError } from '../utils/errors.js';

declare module 'fastify' {
  interface FastifyRequest {
    deviceId?: string;
    merchantId?: string;
  }
}

export function createDeviceJwtMiddleware(
  authService: AuthService,
  deviceRepo: DeviceRepository,
) {
  return async (request: FastifyRequest, reply: FastifyReply) => {
    const header = request.headers.authorization;
    if (!header?.startsWith('Bearer ')) {
      throw new UnauthorizedError('Missing or invalid authorization header');
    }

    const token = header.slice(7);
    try {
      const payload = authService.verifyDeviceToken(token);
      request.deviceId = payload.device_id;
      request.merchantId = payload.sub;

      const device = await deviceRepo.findById(payload.device_id);
      if (!device) throw new ForbiddenError('Device not found');
      if (device.status === 'suspended' || device.status === 'deregistered') {
        throw new ForbiddenError('Device is suspended or deregistered');
      }
      if (device.merchant_id !== payload.sub) {
        throw new ForbiddenError('Device does not belong to this merchant');
      }
    } catch (error) {
      if (error instanceof ForbiddenError) throw error;
      throw new UnauthorizedError('Invalid or expired device token');
    }
  };
}

export function createAdminJwtMiddleware(authService: AuthService) {
  return async (request: FastifyRequest, reply: FastifyReply) => {
    const header = request.headers.authorization;
    if (!header?.startsWith('Bearer ')) {
      throw new UnauthorizedError('Missing or invalid authorization header');
    }

    const token = header.slice(7);
    try {
      const payload = authService.verifyAdminToken(token);
      request.merchantId = payload.sub;
      (request as any).adminId = payload.sub;
      (request as any).adminRole = payload.role;
    } catch {
      throw new UnauthorizedError('Invalid or expired admin token');
    }
  };
}
