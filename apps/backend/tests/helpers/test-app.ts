import Fastify from 'fastify';
import cors from '@fastify/cors';
import websocket from '@fastify/websocket';
import { vi } from 'vitest';
import { getEnv } from '../../src/config/env.js';
import { AuthService } from '../../src/services/auth.service.js';
import { PaymentService } from '../../src/services/payment.service.js';
import { MerchantService } from '../../src/services/merchant.service.js';
import { ManualDetector } from '../../src/detection/manual.detector.js';
import { createJwtMiddleware } from '../../src/middleware/auth.middleware.js';
import { createApiKeyMiddleware } from '../../src/middleware/api-key.middleware.js';
import { registerAuthRoutes } from '../../src/routes/auth.routes.js';
import { registerPaymentLinkRoutes } from '../../src/routes/payment-link.routes.js';
import { registerPaymentRoutes } from '../../src/routes/payment.routes.js';
import { registerWebhookRoutes } from '../../src/routes/webhook.routes.js';
import { registerApiKeyRoutes } from '../../src/routes/api-key.routes.js';
import { registerAccountRoutes } from '../../src/routes/account.routes.js';
import { registerCheckoutRoutes } from '../../src/routes/checkout.routes.js';
import { AppError } from '../../src/utils/errors.js';
import {
  createMockMerchantRepo,
  createMockPaymentRepo,
  createMockPaymentEventRepo,
  createMockApiKeyRepo,
  createMockWebhookLogRepo,
  createMockWebhookService,
  createMockQrService,
  createMockUpiService,
} from './mocks.js';

export async function createTestApp() {
  const env = getEnv();

  const merchantRepo = createMockMerchantRepo();
  const paymentRepo = createMockPaymentRepo();
  const eventRepo = createMockPaymentEventRepo();
  const apiKeyRepo = createMockApiKeyRepo();
  const webhookLogRepo = createMockWebhookLogRepo();
  const webhookService = createMockWebhookService();
  const qrService = createMockQrService();
  const upiService = createMockUpiService();

  const authService = new AuthService(env);
  const paymentService = new PaymentService(
    paymentRepo as any,
    eventRepo as any,
    merchantRepo as any,
    upiService as any,
    qrService as any,
    webhookService as any,
    env,
  );
  const merchantService = new MerchantService(merchantRepo as any, authService);
  const manualDetector = new ManualDetector(paymentService, env.INTERNAL_API_KEY);

  const app = Fastify({ logger: false });
  await app.register(cors);
  await app.register(websocket);

  app.setErrorHandler((error: Error, request, reply) => {
    if (error instanceof AppError) {
      return reply.status(error.statusCode).send(error.toJSON());
    }
    return reply.status(500).send({
      error: { code: 'INTERNAL_ERROR', message: error.message },
    });
  });

  const jwtMiddleware = createJwtMiddleware(authService);
  const apiKeyMiddleware = createApiKeyMiddleware(authService, apiKeyRepo as any);
  const combinedAuth = async (request: any, reply: any) => {
    if (request.headers['x-api-key']) return apiKeyMiddleware(request, reply);
    return jwtMiddleware(request, reply);
  };

  registerAuthRoutes(app, merchantService);
  registerPaymentLinkRoutes(app, paymentService, combinedAuth);
  registerPaymentRoutes(app, paymentService, combinedAuth);
  registerWebhookRoutes(app, webhookService as any, merchantRepo as any, webhookLogRepo as any, combinedAuth);
  registerApiKeyRoutes(app, authService, apiKeyRepo as any, combinedAuth);
  registerAccountRoutes(app, merchantService, combinedAuth);
  registerCheckoutRoutes(app, paymentService, merchantRepo as any);
  manualDetector.registerRoutes(app);

  app.get('/health', async () => ({ status: 'ok' }));

  await app.ready();

  return {
    app,
    repos: { merchantRepo, paymentRepo, eventRepo, apiKeyRepo, webhookLogRepo },
    services: { authService, paymentService, merchantService, webhookService },
    getAuthHeader: (merchantId: string) => ({
      authorization: `Bearer ${authService.generateAccessToken(merchantId)}`,
    }),
  };
}
