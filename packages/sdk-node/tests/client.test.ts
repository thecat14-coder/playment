import { describe, it, expect, vi, beforeEach } from 'vitest';
import { GatewayClient } from '../src/client.js';
import { GatewayApiError } from '../src/types.js';

const mockFetch = vi.fn();
globalThis.fetch = mockFetch;

function jsonResponse(body: unknown, status = 200) {
  return {
    ok: status >= 200 && status < 300,
    status,
    statusText: status === 200 ? 'OK' : 'Error',
    json: async () => body,
  };
}

describe('GatewayClient', () => {
  let client: GatewayClient;

  beforeEach(() => {
    mockFetch.mockReset();
    client = new GatewayClient({
      apiKey: 'gw_live_testapikey123',
      baseUrl: 'https://api.test.com',
    });
  });

  it('should use default base URL when none provided', () => {
    const defaultClient = new GatewayClient({ apiKey: 'test' });
    mockFetch.mockResolvedValue(jsonResponse({ data: {} }));
    defaultClient.getAccount();
    expect(mockFetch).toHaveBeenCalledWith(
      expect.stringContaining('https://api.gateway.com'),
      expect.any(Object),
    );
  });

  it('should strip trailing slashes from base URL', () => {
    const slashClient = new GatewayClient({ apiKey: 'test', baseUrl: 'https://api.test.com///' });
    mockFetch.mockResolvedValue(jsonResponse({ data: {} }));
    slashClient.getAccount();
    expect(mockFetch).toHaveBeenCalledWith(
      expect.stringContaining('https://api.test.com/v1/account'),
      expect.any(Object),
    );
  });

  it('should send X-Api-Key header', async () => {
    mockFetch.mockResolvedValue(jsonResponse({ data: {} }));
    await client.getAccount();
    expect(mockFetch).toHaveBeenCalledWith(
      expect.any(String),
      expect.objectContaining({
        headers: expect.objectContaining({ 'X-Api-Key': 'gw_live_testapikey123' }),
      }),
    );
  });

  describe('createPaymentLink', () => {
    it('should POST to /v1/payment-links', async () => {
      const mockResponse = { data: { id: 'pay_123', status: 'pending' } };
      mockFetch.mockResolvedValue(jsonResponse(mockResponse, 200));

      const result = await client.createPaymentLink({
        amount: 10000,
        currency: 'INR',
        order_id: 'ORD-1',
      });

      expect(mockFetch).toHaveBeenCalledWith(
        'https://api.test.com/v1/payment-links',
        expect.objectContaining({
          method: 'POST',
          body: JSON.stringify({ amount: 10000, currency: 'INR', order_id: 'ORD-1' }),
        }),
      );
      expect(result.data.id).toBe('pay_123');
    });
  });

  describe('getPaymentLink', () => {
    it('should GET /v1/payment-links/:id', async () => {
      mockFetch.mockResolvedValue(jsonResponse({ data: { id: 'pay_123' } }));
      await client.getPaymentLink('pay_123');
      expect(mockFetch).toHaveBeenCalledWith(
        'https://api.test.com/v1/payment-links/pay_123',
        expect.objectContaining({ method: 'GET' }),
      );
    });
  });

  describe('listPayments', () => {
    it('should pass query params for filters', async () => {
      mockFetch.mockResolvedValue(jsonResponse({ data: [], pagination: {} }));
      await client.listPayments({ status: 'success' as any, page: 2, limit: 10 });
      const calledUrl = mockFetch.mock.calls[0][0];
      expect(calledUrl).toContain('status=success');
      expect(calledUrl).toContain('page=2');
      expect(calledUrl).toContain('limit=10');
    });

    it('should omit undefined query params', async () => {
      mockFetch.mockResolvedValue(jsonResponse({ data: [], pagination: {} }));
      await client.listPayments({ page: 1 } as any);
      const calledUrl = mockFetch.mock.calls[0][0];
      expect(calledUrl).not.toContain('status=');
    });
  });

  describe('updateAccount', () => {
    it('should PATCH /v1/account', async () => {
      mockFetch.mockResolvedValue(jsonResponse({ data: { name: 'Updated' } }));
      await client.updateAccount({ name: 'Updated' });
      expect(mockFetch).toHaveBeenCalledWith(
        'https://api.test.com/v1/account',
        expect.objectContaining({
          method: 'PATCH',
          body: JSON.stringify({ name: 'Updated' }),
        }),
      );
    });
  });

  describe('createApiKey', () => {
    it('should POST /v1/api-keys', async () => {
      mockFetch.mockResolvedValue(jsonResponse({ data: { key: 'gw_live_new' } }));
      await client.createApiKey({ label: 'Test Key' });
      expect(mockFetch).toHaveBeenCalledWith(
        'https://api.test.com/v1/api-keys',
        expect.objectContaining({ method: 'POST' }),
      );
    });
  });

  describe('testWebhook', () => {
    it('should POST /v1/webhooks/test', async () => {
      mockFetch.mockResolvedValue(jsonResponse({ data: { success: true } }));
      await client.testWebhook();
      expect(mockFetch).toHaveBeenCalledWith(
        'https://api.test.com/v1/webhooks/test',
        expect.objectContaining({ method: 'POST' }),
      );
    });
  });

  describe('error handling', () => {
    it('should throw GatewayApiError on non-2xx response', async () => {
      mockFetch.mockResolvedValue(jsonResponse(
        { error: { code: 'NOT_FOUND', message: 'Payment not found' } },
        404,
      ));

      await expect(client.getPaymentLink('bad')).rejects.toThrow(GatewayApiError);

      try {
        await client.getPaymentLink('bad');
      } catch (e) {
        const err = e as GatewayApiError;
        expect(err.statusCode).toBe(404);
        expect(err.code).toBe('NOT_FOUND');
        expect(err.message).toBe('Payment not found');
      }
    });

    it('should throw GatewayApiError with UNKNOWN_ERROR when response body is not JSON', async () => {
      mockFetch.mockResolvedValue({
        ok: false,
        status: 500,
        statusText: 'Internal Server Error',
        json: async () => { throw new Error('not json'); },
      });

      await expect(client.getAccount()).rejects.toThrow(GatewayApiError);

      try {
        await client.getAccount();
      } catch (e) {
        const err = e as GatewayApiError;
        expect(err.code).toBe('UNKNOWN_ERROR');
        expect(err.statusCode).toBe(500);
      }
    });

    it('should include details when present in error response', async () => {
      mockFetch.mockResolvedValue(jsonResponse(
        {
          error: {
            code: 'VALIDATION_ERROR',
            message: 'Invalid input',
            details: [{ field: 'amount', issue: 'Must be positive' }],
          },
        },
        400,
      ));

      try {
        await client.createPaymentLink({ amount: -1, currency: 'INR', order_id: 'X' });
      } catch (e) {
        const err = e as GatewayApiError;
        expect(err.details).toEqual([{ field: 'amount', issue: 'Must be positive' }]);
      }
    });
  });
});
