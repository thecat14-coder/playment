import Fastify from 'fastify';
import cors from '@fastify/cors';
import helmet from '@fastify/helmet';
import websocket from '@fastify/websocket';
import { getEnv } from './config/env.js';
import { createContainer } from './container.js';
import { createJwtMiddleware } from './middleware/auth.middleware.js';
import { createApiKeyMiddleware } from './middleware/api-key.middleware.js';
import { createDeviceJwtMiddleware, createAdminJwtMiddleware } from './middleware/device-auth.middleware.js';
import { registerRateLimit } from './middleware/rate-limit.middleware.js';
import { registerAuthRoutes } from './routes/auth.routes.js';
import { registerPaymentLinkRoutes } from './routes/payment-link.routes.js';
import { registerPaymentRoutes } from './routes/payment.routes.js';
import { registerWebhookRoutes } from './routes/webhook.routes.js';
import { registerApiKeyRoutes } from './routes/api-key.routes.js';
import { registerAccountRoutes } from './routes/account.routes.js';
import { registerCheckoutRoutes } from './routes/checkout.routes.js';
import { registerDeviceRoutes } from './routes/device.routes.js';
import { registerEvidenceRoutes } from './routes/evidence.routes.js';
import { registerStoreRoutes } from './routes/store.routes.js';
import { registerReviewRoutes } from './routes/review.routes.js';
import { registerHealthRoutes } from './routes/health.routes.js';
import { registerAdminRoutes } from './routes/admin.routes.js';
import { registerWebSocket } from './websocket/payment-status.ws.js';
import { AppError } from './utils/errors.js';

async function main() {
  const env = getEnv();

  const app = Fastify({
    logger: {
      level: env.LOG_LEVEL,
      transport: env.NODE_ENV === 'development'
        ? { target: 'pino-pretty', options: { colorize: true } }
        : undefined,
    },
  });

  // Liveness — register before Redis/DB-dependent plugins so probes respond quickly
  app.get('/health', async () => ({ status: 'ok' }));

  const container = createContainer(env);

  await app.register(cors, {
    origin: [env.DASHBOARD_URL, env.CHECKOUT_URL, env.ADMIN_URL],
    credentials: true,
  });

  await app.register(helmet);
  await app.register(websocket);
  await registerRateLimit(app, container.redis);

  app.setErrorHandler((error: Error, request, reply) => {
    if (error instanceof AppError) {
      return reply.status(error.statusCode).send(error.toJSON());
    }

    const fastifyError = error as Error & { validation?: unknown; statusCode?: number };
    if (fastifyError.validation) {
      return reply.status(400).send({
        error: {
          code: 'VALIDATION_ERROR',
          message: error.message,
        },
      });
    }

    request.log.error(error);
    return reply.status(500).send({
      error: {
        code: 'INTERNAL_ERROR',
        message: env.NODE_ENV === 'production' ? 'Internal server error' : error.message,
      },
    });
  });

  const jwtMiddleware = createJwtMiddleware(container.services.authService);
  const apiKeyMiddleware = createApiKeyMiddleware(
    container.services.authService,
    container.repos.apiKeyRepo,
  );
  const deviceAuthMiddleware = createDeviceJwtMiddleware(
    container.services.authService,
    container.repos.deviceRepo,
  );
  const adminJwtMiddleware = createAdminJwtMiddleware(container.services.authService);

  const combinedAuthMiddleware = async (request: any, reply: any) => {
    if (request.headers['x-api-key']) {
      return apiKeyMiddleware(request, reply);
    }
    return jwtMiddleware(request, reply);
  };

  // V1 Routes
  registerAuthRoutes(app, container.services.merchantService);
  registerPaymentLinkRoutes(app, container.services.paymentService, combinedAuthMiddleware);
  registerPaymentRoutes(app, container.services.paymentService, combinedAuthMiddleware);
  registerWebhookRoutes(
    app,
    container.services.webhookService,
    container.repos.merchantRepo,
    container.repos.webhookLogRepo,
    combinedAuthMiddleware,
  );
  registerApiKeyRoutes(
    app,
    container.services.authService,
    container.repos.apiKeyRepo,
    combinedAuthMiddleware,
  );
  registerAccountRoutes(app, container.services.merchantService, combinedAuthMiddleware);
  registerCheckoutRoutes(app, container.services.paymentService, container.repos.merchantRepo);
  registerWebSocket(app, container.services.paymentService);

  // V2 Routes
  registerDeviceRoutes(
    app,
    container.services.deviceService,
    container.services.healthService,
    deviceAuthMiddleware,
    jwtMiddleware,
  );
  registerEvidenceRoutes(
    app,
    container.services.evidenceService,
    container.services.matchingService,
    container.queues.matchingQueue,
    deviceAuthMiddleware,
    jwtMiddleware,
  );
  registerStoreRoutes(app, container.services.storeService, jwtMiddleware);
  registerReviewRoutes(app, container.services.reviewService, jwtMiddleware);
  registerHealthRoutes(app, container.services.healthService, jwtMiddleware);
  registerAdminRoutes(
    app,
    container.repos.merchantRepo,
    container.repos.deviceRepo,
    container.repos.evidenceRepo,
    container.services.reviewService,
    container.services.matchingService,
    container.services.deviceService,
    container.repos.paymentRepo,
    container.repos.auditLogRepo,
    adminJwtMiddleware,
  );

  const shutdown = async () => {
    app.log.info('Shutting down...');
    container.workers.expiryWorker.stop();
    await container.workers.webhookWorker.close();
    await container.workers.matchingWorker.close();
    await container.workers.healthWorker.close();
    await container.workers.notificationLogWorker.close();
    await app.close();
    await container.redis.quit();
    await container.pgClient.end();
    process.exit(0);
  };

  process.on('SIGTERM', shutdown);
  process.on('SIGINT', shutdown);

  await app.listen({ port: env.PORT, host: '0.0.0.0' });
  app.log.info(`Server running at http://localhost:${env.PORT}`);

  container.workers.expiryWorker.start();
  app.log.info('Expiry worker started');

  container.workers.webhookWorker.on('completed', (job) => {
    app.log.debug({ jobId: job.id }, 'Webhook delivered');
  });
  container.workers.webhookWorker.on('failed', (job, error) => {
    app.log.warn({ jobId: job?.id, error: error.message }, 'Webhook delivery failed');
  });

  // V2 Workers are event-driven via BullMQ queues
  container.workers.matchingWorker.on('completed', (job) => {
    app.log.debug({ jobId: job.id, evidenceId: job.data.evidenceId }, 'Matching completed');
  });
  container.workers.matchingWorker.on('failed', (job, error) => {
    app.log.warn({ jobId: job?.id, error: error.message }, 'Matching failed');
  });

  container.workers.healthWorker.on('completed', (job) => {
    app.log.debug({ jobId: job.id }, 'Health processing completed');
  });

  container.workers.notificationLogWorker.on('completed', (job) => {
    app.log.debug({ jobId: job.id }, 'Notification log written');
  });
}

main().catch((err) => {
  console.error('Failed to start server:', err);
  process.exit(1);
});
