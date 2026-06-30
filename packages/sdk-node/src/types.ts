export type {
  CreatePaymentLinkInput,
  CreatePaymentLinkResponse,
  PaymentListFilterInput,
  PaginationInput,
  UpdateAccountInput,
  CreateApiKeyInput,
  ApiResponse,
  PaginatedResponse,
  ApiErrorResponse,
  Payment,
  Merchant,
  ApiKey,
  WebhookLog,
  WebhookPayload,
} from "@gateway/shared";

export interface GatewayClientOptions {
  apiKey: string;
  baseUrl?: string;
}

export class GatewayApiError extends Error {
  public readonly statusCode: number;
  public readonly code: string;
  public readonly details?: Array<{ field: string; issue: string }>;

  constructor(
    statusCode: number,
    code: string,
    message: string,
    details?: Array<{ field: string; issue: string }>,
  ) {
    super(message);
    this.name = "GatewayApiError";
    this.statusCode = statusCode;
    this.code = code;
    this.details = details;
  }
}
