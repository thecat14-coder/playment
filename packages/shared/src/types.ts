import type {
  PaymentStatus, MerchantStatus, DeviceStatus, ConfidenceLevel,
  MatchingDecision, ReviewStatus, ReviewerType, HealthLevel,
  AuditActorType, PaymentEventType, EventSource,
} from './constants.js';

// ── Existing (V1) ──

export interface Merchant {
  id: string;
  name: string;
  email: string;
  upi_id: string;
  webhook_url: string | null;
  webhook_secret: string;
  logo_url: string | null;
  status: MerchantStatus;
  health_score: number;
  active_device_count: number;
  created_at: Date;
  updated_at: Date;
}

export interface Payment {
  id: string;
  merchant_id: string;
  store_id: string | null;
  amount: number;
  currency: string;
  status: PaymentStatus;
  order_id: string;
  customer_name: string | null;
  customer_email: string | null;
  customer_phone: string | null;
  upi_intent: string;
  qr_url: string;
  payment_link: string;
  metadata: Record<string, unknown> | null;
  evidence_id: string | null;
  confidence: number | null;
  matched_at: Date | null;
  expires_at: Date;
  paid_at: Date | null;
  created_at: Date;
}

export interface PaymentEvent {
  id: string;
  payment_id: string;
  type: PaymentEventType;
  payload: Record<string, unknown>;
  source: EventSource;
  created_at: Date;
}

export interface ApiKey {
  id: string;
  merchant_id: string;
  key_hash: string;
  key_prefix: string;
  label: string | null;
  last_used_at: Date | null;
  is_active: boolean;
  created_at: Date;
}

export interface WebhookLog {
  id: string;
  payment_id: string;
  merchant_id: string;
  url: string;
  request_body: Record<string, unknown>;
  response_status: number | null;
  response_body: string | null;
  attempt: number;
  delivered: boolean;
  error: string | null;
  created_at: Date;
}

// ── V2: Device Management ──

export interface Store {
  id: string;
  merchant_id: string;
  name: string;
  address: string | null;
  upi_id: string | null;
  is_active: boolean;
  created_at: Date;
}

export interface Counter {
  id: string;
  store_id: string;
  label: string;
  is_active: boolean;
  created_at: Date;
}

export interface MerchantDevice {
  id: string;
  device_uuid: string;
  merchant_id: string;
  store_id: string | null;
  counter_id: string | null;
  model: string;
  manufacturer: string;
  android_version: string;
  app_version: string;
  fcm_token: string | null;
  status: DeviceStatus;
  is_online: boolean;
  notification_permission: boolean;
  battery_optimization_disabled: boolean;
  health_score: number;
  last_heartbeat_at: Date | null;
  last_notification_at: Date | null;
  device_secret: string;
  registered_at: Date;
}

export interface DeviceHeartbeat {
  id: string;
  device_id: string;
  listener_running: boolean;
  created_at: Date;
}

// ── V2: Evidence & Matching ──

export interface PaymentEvidence {
  id: string;
  device_id: string;
  merchant_id: string;
  raw_notification: string;
  amount: number;
  utr: string | null;
  rrn: string | null;
  sender_vpa: string | null;
  sender_name: string | null;
  upi_app: string;
  bank: string | null;
  notification_package: string;
  notification_timestamp: Date;
  uploaded_at: Date;
  parser_version: string;
  nonce: string;
  signature: string;
  matched: boolean;
  matched_payment_id: string | null;
}

export interface MatchingResult {
  id: string;
  evidence_id: string;
  payment_id: string | null;
  confidence: number;
  confidence_level: ConfidenceLevel;
  score_breakdown: Record<string, number>;
  decision: MatchingDecision;
  matched_at: Date;
}

// ── V2: Manual Review ──

export interface ManualReview {
  id: string;
  payment_id: string;
  evidence_id: string | null;
  matching_result_id: string | null;
  status: ReviewStatus;
  reviewer_id: string | null;
  reviewer_type: ReviewerType | null;
  reason: string | null;
  created_at: Date;
  resolved_at: Date | null;
}

// ── V2: Health ──

export interface HealthReport {
  id: string;
  device_id: string;
  health_score: number;
  notification_permission: boolean;
  battery_optimization_disabled: boolean;
  foreground_service_running: boolean;
  listener_running: boolean;
  internet_connected: boolean;
  battery_level: number;
  app_version: string;
  created_at: Date;
}

// ── V2: Notification Log ──

export interface NotificationLog {
  id: string;
  device_id: string;
  package_name: string;
  title: string | null;
  body: string;
  is_payment: boolean;
  evidence_id: string | null;
  received_at: Date;
}

// ── V2: Audit ──

export interface AuditLog {
  id: string;
  actor_type: AuditActorType;
  actor_id: string;
  action: string;
  resource_type: string;
  resource_id: string;
  details: Record<string, unknown> | null;
  ip_address: string | null;
  created_at: Date;
}

// ── API Response Types ──

export interface ApiResponse<T> {
  data: T;
}

export interface ApiErrorResponse {
  error: {
    code: string;
    message: string;
    details?: Array<{ field: string; issue: string }>;
  };
}

export interface PaginatedResponse<T> {
  data: T[];
  pagination: {
    page: number;
    limit: number;
    total: number;
    total_pages: number;
  };
}

export interface CreatePaymentLinkResponse {
  id: string;
  payment_link: string;
  qr_url: string;
  upi_intent: string;
  amount: number;
  currency: string;
  status: PaymentStatus;
  order_id: string;
  expires_at: string;
  created_at: string;
}

export interface WebhookPayload {
  event: PaymentEventType;
  payment_id: string;
  order_id: string;
  amount: number;
  currency: string;
  status: PaymentStatus;
  paid_at: string | null;
  confidence: number | null;
  metadata: Record<string, unknown> | null;
}

export interface DeviceRegistrationResponse {
  device_id: string;
  device_secret: string;
  status: DeviceStatus;
}

export interface HealthSummary {
  merchant_id: string;
  health_score: number;
  health_level: HealthLevel;
  active_devices: number;
  online_devices: number;
  devices: Array<{
    device_id: string;
    model: string;
    health_score: number;
    health_level: HealthLevel;
    is_online: boolean;
    last_heartbeat_at: string | null;
  }>;
}
