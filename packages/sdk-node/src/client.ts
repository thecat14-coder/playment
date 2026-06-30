import type {
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
} from "@gateway/shared";
import { GatewayApiError, type GatewayClientOptions } from "./types.js";

const DEFAULT_BASE_URL = "https://api.gateway.com";

export class GatewayClient {
  private readonly apiKey: string;
  private readonly baseUrl: string;

  constructor(options: GatewayClientOptions) {
    this.apiKey = options.apiKey;
    this.baseUrl = (options.baseUrl ?? DEFAULT_BASE_URL).replace(/\/+$/, "");
  }

  async createPaymentLink(
    input: CreatePaymentLinkInput,
  ): Promise<ApiResponse<CreatePaymentLinkResponse>> {
    return this.request("POST", "/v1/payment-links", input);
  }

  async getPaymentLink(
    id: string,
  ): Promise<ApiResponse<CreatePaymentLinkResponse>> {
    return this.request("GET", `/v1/payment-links/${id}`);
  }

  async listPaymentLinks(
    filters?: PaginationInput,
  ): Promise<PaginatedResponse<CreatePaymentLinkResponse>> {
    return this.request("GET", "/v1/payment-links", undefined, filters);
  }

  async getPayment(id: string): Promise<ApiResponse<Payment>> {
    return this.request("GET", `/v1/payments/${id}`);
  }

  async listPayments(
    filters?: PaymentListFilterInput,
  ): Promise<PaginatedResponse<Payment>> {
    return this.request("GET", "/v1/payments", undefined, filters);
  }

  async getAccount(): Promise<ApiResponse<Merchant>> {
    return this.request("GET", "/v1/account");
  }

  async updateAccount(
    input: UpdateAccountInput,
  ): Promise<ApiResponse<Merchant>> {
    return this.request("PATCH", "/v1/account", input);
  }

  async createApiKey(
    input?: CreateApiKeyInput,
  ): Promise<ApiResponse<ApiKey & { key: string }>> {
    return this.request("POST", "/v1/api-keys", input);
  }

  async listApiKeys(): Promise<ApiResponse<ApiKey[]>> {
    return this.request("GET", "/v1/api-keys");
  }

  async testWebhook(): Promise<ApiResponse<{ success: boolean }>> {
    return this.request("POST", "/v1/webhooks/test");
  }

  async listWebhookLogs(
    pagination?: PaginationInput,
  ): Promise<PaginatedResponse<WebhookLog>> {
    return this.request("GET", "/v1/webhooks/logs", undefined, pagination);
  }

  private async request<T>(
    method: string,
    path: string,
    body?: unknown,
    query?: Record<string, unknown>,
  ): Promise<T> {
    const url = new URL(`${this.baseUrl}${path}`);

    if (query) {
      for (const [key, value] of Object.entries(query)) {
        if (value !== undefined && value !== null) {
          url.searchParams.set(
            key,
            value instanceof Date ? value.toISOString() : String(value),
          );
        }
      }
    }

    const headers: Record<string, string> = {
      "X-Api-Key": this.apiKey,
      "Content-Type": "application/json",
    };

    const response = await fetch(url.toString(), {
      method,
      headers,
      body: body ? JSON.stringify(body) : undefined,
    });

    if (!response.ok) {
      let errorBody: ApiErrorResponse | undefined;
      try {
        errorBody = (await response.json()) as ApiErrorResponse;
      } catch {
        throw new GatewayApiError(
          response.status,
          "UNKNOWN_ERROR",
          response.statusText,
        );
      }

      throw new GatewayApiError(
        response.status,
        errorBody.error.code,
        errorBody.error.message,
        errorBody.error.details,
      );
    }

    return (await response.json()) as T;
  }
}
