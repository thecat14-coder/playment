import { z } from 'zod';
import { AMOUNT, DEFAULT_PAYMENT_EXPIRY_SECONDS } from './constants.js';

// ── Payment ──

export const createPaymentLinkSchema = z.object({
  amount: z.number().int().min(AMOUNT.MIN_PAISE).max(AMOUNT.MAX_PAISE),
  currency: z.literal('INR').default('INR'),
  order_id: z.string().min(1).max(255),
  store_id: z.string().max(26).optional(),
  customer_name: z.string().max(255).optional(),
  customer_email: z.string().email().max(255).optional(),
  customer_phone: z.string().max(20).optional(),
  expires_in: z.number().int().min(60).max(86400).default(DEFAULT_PAYMENT_EXPIRY_SECONDS),
  metadata: z.record(z.unknown()).optional(),
});

export type CreatePaymentLinkInput = z.infer<typeof createPaymentLinkSchema>;

// ── Auth ──

export const registerMerchantSchema = z.object({
  name: z.string().min(1).max(255),
  email: z.string().email().max(255),
  password: z.string().min(8).max(128),
  upi_id: z.string().min(3).max(255).optional(),
});

export type RegisterMerchantInput = z.infer<typeof registerMerchantSchema>;

export const loginSchema = z.object({
  email: z.string().email(),
  password: z.string().min(1),
});

export type LoginInput = z.infer<typeof loginSchema>;

export const updateAccountSchema = z.object({
  name: z.string().min(1).max(255).optional(),
  upi_id: z.string().min(3).max(255).optional(),
  webhook_url: z
    .string()
    .url()
    .max(2048)
    .refine((url) => url.startsWith('https://'), {
      message: 'Webhook URL must use HTTPS',
    })
    .optional(),
});

export type UpdateAccountInput = z.infer<typeof updateAccountSchema>;

// ── API Keys ──

export const createApiKeySchema = z.object({
  label: z.string().max(255).optional(),
});

export type CreateApiKeyInput = z.infer<typeof createApiKeySchema>;

export const updateApiKeySchema = z.object({
  label: z.string().max(255).optional(),
  is_active: z.boolean().optional(),
});

export type UpdateApiKeyInput = z.infer<typeof updateApiKeySchema>;

// ── Pagination & Filters ──

export const paginationSchema = z.object({
  page: z.coerce.number().int().min(1).default(1),
  limit: z.coerce.number().int().min(1).max(100).default(20),
});

export type PaginationInput = z.infer<typeof paginationSchema>;

export const paymentListFilterSchema = paginationSchema.extend({
  status: z.enum(['pending', 'matching', 'review', 'success', 'failed', 'expired']).optional(),
  from: z.coerce.date().optional(),
  to: z.coerce.date().optional(),
});

export type PaymentListFilterInput = z.infer<typeof paymentListFilterSchema>;

// ── V2: Device ──

export const deviceRegisterSchema = z.object({
  device_uuid: z.string().uuid(),
  model: z.string().min(1).max(255),
  manufacturer: z.string().min(1).max(255),
  android_version: z.string().min(1).max(20),
  app_version: z.string().min(1).max(20),
  fcm_token: z.string().max(512).optional(),
});

export type DeviceRegisterInput = z.infer<typeof deviceRegisterSchema>;

export const deviceHeartbeatSchema = z.object({
  listener_running: z.boolean(),
});

export type DeviceHeartbeatInput = z.infer<typeof deviceHeartbeatSchema>;

export const deviceStatusSchema = z.object({
  is_online: z.boolean(),
});

export type DeviceStatusInput = z.infer<typeof deviceStatusSchema>;

export const deviceAssignSchema = z.object({
  store_id: z.string().max(26).nullable(),
  counter_id: z.string().max(26).nullable().optional(),
});

export type DeviceAssignInput = z.infer<typeof deviceAssignSchema>;

// ── V2: Store ──

export const createStoreSchema = z.object({
  name: z.string().min(1).max(255),
  address: z.string().max(1000).optional(),
  upi_id: z.string().min(3).max(255).optional(),
});

export type CreateStoreInput = z.infer<typeof createStoreSchema>;

export const updateStoreSchema = z.object({
  name: z.string().min(1).max(255).optional(),
  address: z.string().max(1000).optional(),
  upi_id: z.string().min(3).max(255).optional(),
  is_active: z.boolean().optional(),
});

export type UpdateStoreInput = z.infer<typeof updateStoreSchema>;

export const createCounterSchema = z.object({
  label: z.string().min(1).max(255),
});

export type CreateCounterInput = z.infer<typeof createCounterSchema>;

// ── V2: Evidence ──

export const evidenceUploadSchema = z.object({
  raw_notification: z.string().min(1).max(4096),
  amount: z.number().int().min(1),
  utr: z.string().max(50).optional(),
  rrn: z.string().max(50).optional(),
  sender_vpa: z.string().max(255).optional(),
  sender_name: z.string().max(255).optional(),
  upi_app: z.string().min(1).max(255),
  bank: z.string().max(255).optional(),
  notification_package: z.string().min(1).max(255),
  notification_timestamp: z.coerce.date(),
  parser_version: z.string().min(1).max(20),
  nonce: z.string().uuid(),
  signature: z.string().min(1).max(256),
});

export type EvidenceUploadInput = z.infer<typeof evidenceUploadSchema>;

// ── V2: Health ──

export const healthReportSchema = z.object({
  notification_permission: z.boolean(),
  battery_optimization_disabled: z.boolean(),
  foreground_service_running: z.boolean(),
  listener_running: z.boolean(),
  internet_connected: z.boolean(),
  battery_level: z.number().int().min(0).max(100),
  app_version: z.string().min(1).max(20),
});

export type HealthReportInput = z.infer<typeof healthReportSchema>;

// ── V2: Review ──

export const reviewActionSchema = z.object({
  reason: z.string().max(1000).optional(),
});

export type ReviewActionInput = z.infer<typeof reviewActionSchema>;
