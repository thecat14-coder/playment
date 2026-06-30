export interface EvidenceSource {
  readonly name: string; // 'android_notification' | 'bank_api' | 'psp_webhook'
  start(): Promise<void>;
  stop(): Promise<void>;
}

export interface PaymentDetector {
  markSuccess(paymentId: string, metadata?: Record<string, unknown>): Promise<void>;
  markFailed(paymentId: string, reason: string): Promise<void>;
}
