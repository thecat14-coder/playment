import type { Env } from './config/env.js';
import { createDatabase } from './config/database.js';
import { createRedis } from './config/redis.js';
import { MerchantRepository } from './repositories/merchant.repository.js';
import { PaymentRepository } from './repositories/payment.repository.js';
import { PaymentEventRepository } from './repositories/payment-event.repository.js';
import { ApiKeyRepository } from './repositories/api-key.repository.js';
import { WebhookLogRepository } from './repositories/webhook-log.repository.js';
import { EvidenceRepository } from './repositories/evidence.repository.js';
import { MatchingRepository } from './repositories/matching.repository.js';
import { DeviceRepository } from './repositories/device.repository.js';
import { HealthRepository } from './repositories/health.repository.js';
import { ReviewRepository } from './repositories/review.repository.js';
import { StoreRepository } from './repositories/store.repository.js';
import { AuditLogRepository } from './repositories/audit-log.repository.js';
import { AuthService } from './services/auth.service.js';
import { UpiService } from './services/upi.service.js';
import { QrService } from './services/qr.service.js';
import { WebhookService } from './services/webhook.service.js';
import { PaymentService } from './services/payment.service.js';
import { MerchantService } from './services/merchant.service.js';
import { EvidenceService } from './services/evidence.service.js';
import { MatchingService } from './services/matching.service.js';
import { DeviceService } from './services/device.service.js';
import { HealthService } from './services/health.service.js';
import { ReviewService } from './services/review.service.js';
import { StoreService } from './services/store.service.js';
import { NotificationLogRepository } from './services/notification-log.service.js';
import { ExpiryWorker } from './workers/expiry.worker.js';
import { createWebhookWorker } from './workers/webhook.worker.js';
import { createMatchingQueue, createMatchingWorker } from './workers/matching.worker.js';
import { createHealthQueue, createHealthWorker } from './workers/health.worker.js';
import { createNotificationLogQueue, createNotificationLogWorker } from './workers/notification-log.worker.js';

export function createContainer(env: Env) {
  const { db, client: pgClient } = createDatabase(env);
  const redis = createRedis(env);

  // Repositories
  const merchantRepo = new MerchantRepository(db);
  const paymentRepo = new PaymentRepository(db);
  const eventRepo = new PaymentEventRepository(db);
  const apiKeyRepo = new ApiKeyRepository(db);
  const webhookLogRepo = new WebhookLogRepository(db);
  const evidenceRepo = new EvidenceRepository(db);
  const matchingRepo = new MatchingRepository(db);
  const deviceRepo = new DeviceRepository(db);
  const healthRepo = new HealthRepository(db);
  const reviewRepo = new ReviewRepository(db);
  const storeRepo = new StoreRepository(db);
  const auditLogRepo = new AuditLogRepository(db);

  // Services
  const authService = new AuthService(env);
  const upiService = new UpiService();
  const qrService = new QrService();
  const webhookService = new WebhookService(redis);
  const paymentService = new PaymentService(
    paymentRepo,
    eventRepo,
    merchantRepo,
    upiService,
    qrService,
    webhookService,
    env,
  );
  const merchantService = new MerchantService(merchantRepo, authService);
  const notificationLogRepo = new NotificationLogRepository(db);
  const evidenceService = new EvidenceService(evidenceRepo, deviceRepo, redis, notificationLogRepo);
  const reviewService = new ReviewService(reviewRepo, paymentRepo, paymentService, webhookService);
  const matchingService = new MatchingService(
    matchingRepo,
    evidenceRepo,
    paymentRepo,
    deviceRepo,
    reviewService,
    paymentService,
  );
  const deviceService = new DeviceService(deviceRepo, healthRepo, authService, redis);
  const healthService = new HealthService(deviceRepo, healthRepo, merchantRepo);
  const storeService = new StoreService(storeRepo);

  // Workers & Queues
  const expiryWorker = new ExpiryWorker(paymentRepo, paymentService);
  const webhookWorker = createWebhookWorker(redis, webhookLogRepo);
  const matchingQueue = createMatchingQueue(redis);
  const matchingWorker = createMatchingWorker(redis, evidenceRepo, paymentRepo, matchingService);
  const healthQueue = createHealthQueue(redis);
  const healthWorker = createHealthWorker(redis, deviceRepo, merchantRepo);
  const notificationLogQueue = createNotificationLogQueue(redis);
  const notificationLogWorker = createNotificationLogWorker(redis, db);

  return {
    db,
    pgClient,
    redis,
    repos: {
      merchantRepo,
      paymentRepo,
      eventRepo,
      apiKeyRepo,
      webhookLogRepo,
      evidenceRepo,
      matchingRepo,
      deviceRepo,
      healthRepo,
      reviewRepo,
      storeRepo,
      auditLogRepo,
    },
    services: {
      authService,
      upiService,
      qrService,
      webhookService,
      paymentService,
      merchantService,
      evidenceService,
      matchingService,
      deviceService,
      healthService,
      reviewService,
      storeService,
      notificationLogRepo,
    },
    queues: {
      matchingQueue,
      healthQueue,
      notificationLogQueue,
    },
    workers: {
      expiryWorker,
      webhookWorker,
      matchingWorker,
      healthWorker,
      notificationLogWorker,
    },
  };
}

export type Container = ReturnType<typeof createContainer>;
