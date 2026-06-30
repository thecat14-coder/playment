import type { FastifyRequest, FastifyReply } from 'fastify';
import type { AuthService } from '../services/auth.service.js';
import { UnauthorizedError } from '../utils/errors.js';

declare module 'fastify' {
  interface FastifyRequest {
    merchantId?: string;
  }
}

export function createJwtMiddleware(authService: AuthService) {
  return async (request: FastifyRequest, reply: FastifyReply) => {
    const header = request.headers.authorization;
    if (!header?.startsWith('Bearer ')) {
      throw new UnauthorizedError('Missing or invalid authorization header');
    }

    const token = header.slice(7);
    try {
      const payload = authService.verifyAccessToken(token);
      request.merchantId = payload.sub;
    } catch {
      throw new UnauthorizedError('Invalid or expired token');
    }
  };
}
