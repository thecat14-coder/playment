export const PaymentStatus = {
  PENDING: 'pending',
  MATCHING: 'matching',
  REVIEW: 'review',
  SUCCESS: 'success',
  FAILED: 'failed',
  EXPIRED: 'expired',
} as const;

export type PaymentStatus = (typeof PaymentStatus)[keyof typeof PaymentStatus];

export const MerchantStatus = {
  ACTIVE: 'active',
  SUSPENDED: 'suspended',
} as const;

export type MerchantStatus = (typeof MerchantStatus)[keyof typeof MerchantStatus];

export const DeviceStatus = {
  REGISTERED: 'registered',
  ACTIVE: 'active',
  SUSPENDED: 'suspended',
  DEREGISTERED: 'deregistered',
} as const;

export type DeviceStatus = (typeof DeviceStatus)[keyof typeof DeviceStatus];

export const ConfidenceLevel = {
  HIGH: 'high',
  MEDIUM: 'medium',
  LOW: 'low',
  UNKNOWN: 'unknown',
} as const;

export type ConfidenceLevel = (typeof ConfidenceLevel)[keyof typeof ConfidenceLevel];

export const MatchingDecision = {
  AUTO_CONFIRMED: 'auto_confirmed',
  FLAGGED: 'flagged',
  REVIEW: 'review',
  DISCARDED: 'discarded',
} as const;

export type MatchingDecision = (typeof MatchingDecision)[keyof typeof MatchingDecision];

export const ReviewStatus = {
  PENDING: 'pending',
  APPROVED: 'approved',
  REJECTED: 'rejected',
  FORCE_FAILED: 'force_failed',
} as const;

export type ReviewStatus = (typeof ReviewStatus)[keyof typeof ReviewStatus];

export const ReviewerType = {
  MERCHANT: 'merchant',
  ADMIN: 'admin',
} as const;

export type ReviewerType = (typeof ReviewerType)[keyof typeof ReviewerType];

export const HealthLevel = {
  EXCELLENT: 'excellent',
  GOOD: 'good',
  WARNING: 'warning',
  CRITICAL: 'critical',
} as const;

export type HealthLevel = (typeof HealthLevel)[keyof typeof HealthLevel];

export const AuditActorType = {
  MERCHANT: 'merchant',
  ADMIN: 'admin',
  SYSTEM: 'system',
  DEVICE: 'device',
} as const;

export type AuditActorType = (typeof AuditActorType)[keyof typeof AuditActorType];

export const PaymentEventType = {
  CREATED: 'payment.created',
  MATCHING: 'payment.matching',
  SUCCESS: 'payment.success',
  FAILED: 'payment.failed',
  EXPIRED: 'payment.expired',
  REVIEW: 'payment.review',
} as const;

export type PaymentEventType = (typeof PaymentEventType)[keyof typeof PaymentEventType];

export const EventSource = {
  API: 'api',
  DETECTOR: 'detector',
  MATCHING: 'matching',
  REVIEW: 'review',
  SYSTEM: 'system',
  DEVICE: 'device',
} as const;

export type EventSource = (typeof EventSource)[keyof typeof EventSource];

export const WEBHOOK_RETRY_DELAYS_MS = [
  30_000,
  60_000,
  300_000,
  900_000,
  1_800_000,
] as const;

export const RATE_LIMITS = {
  PAYMENT_LINK_CREATE: { max: 100, window: 60 },
  PAYMENT_STATUS_QUERY: { max: 300, window: 60 },
  AUTH: { max: 10, window: 900 },
  CHECKOUT: { max: 60, window: 60 },
  WEBHOOK_TEST: { max: 5, window: 60 },
  EVIDENCE_UPLOAD: { max: 120, window: 60 },
  HEARTBEAT: { max: 5, window: 60 },
  DEVICE_HEALTH: { max: 2, window: 60 },
} as const;

export const AMOUNT = {
  MIN_PAISE: 100,
  MAX_PAISE: 50_000_000,
} as const;

export const DEFAULT_PAYMENT_EXPIRY_SECONDS = 1800;

export const API_KEY_PREFIX = 'gw_live_';

export const HEARTBEAT_INTERVAL_SECONDS = 60;
export const HEARTBEAT_STALE_THRESHOLD_SECONDS = 300;
export const HEARTBEAT_DEAD_THRESHOLD_SECONDS = 900;

export const HEALTH_REPORT_INTERVAL_SECONDS = 300;

export const CONFIDENCE_THRESHOLDS = {
  HIGH: 80,
  MEDIUM: 60,
  LOW: 20,
} as const;

export const MATCHING_SCORES = {
  TIME_WITHIN_5MIN: 30,
  TIME_WITHIN_15MIN: 20,
  TIME_WITHIN_30MIN: 10,
  UTR_UNIQUE: 25,
  SINGLE_PENDING_MATCH: 20,
  DEVICE_HEALTH_GOOD: 15,
  DEVICE_HEALTH_WARNING: 8,
  EVIDENCE_FULL: 10,
  EVIDENCE_PARTIAL: 5,
} as const;

export const UPI_APPS = [
  { name: 'PhonePe', scheme: 'phonepe', packageName: 'com.phonepe.app' },
  { name: 'Google Pay', scheme: 'gpay', packageName: 'com.google.android.apps.nbu.paisa.user' },
  { name: 'Paytm', scheme: 'paytmmp', packageName: 'net.one97.paytm' },
  { name: 'BHIM', scheme: 'upi', packageName: 'in.org.npci.upiapp' },
  { name: 'CRED', scheme: 'cred', packageName: 'com.dreamplug.androidapp' },
  { name: 'Amazon Pay', scheme: 'amazonpay', packageName: 'in.amazon.mShop.android.shopping' },
  { name: 'WhatsApp', scheme: 'whatsapp', packageName: 'com.whatsapp' },
] as const;

export function getHealthLevel(score: number): HealthLevel {
  if (score >= 90) return HealthLevel.EXCELLENT;
  if (score >= 70) return HealthLevel.GOOD;
  if (score >= 40) return HealthLevel.WARNING;
  return HealthLevel.CRITICAL;
}

export function getConfidenceLevel(score: number): ConfidenceLevel {
  if (score >= CONFIDENCE_THRESHOLDS.HIGH) return ConfidenceLevel.HIGH;
  if (score >= CONFIDENCE_THRESHOLDS.MEDIUM) return ConfidenceLevel.MEDIUM;
  if (score >= CONFIDENCE_THRESHOLDS.LOW) return ConfidenceLevel.LOW;
  return ConfidenceLevel.UNKNOWN;
}
