import { Worker } from 'bullmq';
import type { WebhookPayload } from '@gateway/shared';
import { WEBHOOK_RETRY_DELAYS_MS } from '@gateway/shared';
import { signWebhookPayload } from '../utils/webhook-signature.js';
import { generateId } from '../utils/ulid.js';
import type { WebhookLogRepository } from '../repositories/webhook-log.repository.js';

export interface WebhookJobData {
  paymentId: string;
  merchantId: string;
  webhookUrl: string;
  webhookSecret: string;
  payload: WebhookPayload;
}

export function createWebhookWorker(
  redis: unknown,
  webhookLogRepo: WebhookLogRepository,
) {
  const worker = new Worker<WebhookJobData>(
    'webhook-delivery',
    async (job) => {
      const { paymentId, merchantId, webhookUrl, webhookSecret, payload } = job.data;
      const bodyStr = JSON.stringify(payload);
      const signature = signWebhookPayload(bodyStr, webhookSecret);
      const attempt = (job.attemptsMade ?? 0) + 1;

      let responseStatus: number | null = null;
      let responseBody: string | null = null;
      let error: string | null = null;
      let delivered = false;

      try {
        const controller = new AbortController();
        const timeout = setTimeout(() => controller.abort(), 10_000);

        const response = await fetch(webhookUrl, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'X-Gateway-Signature': `sha256=${signature}`,
            'X-Gateway-Event': payload.event,
            'X-Gateway-Delivery': generateId(),
          },
          body: bodyStr,
          signal: controller.signal,
        });

        clearTimeout(timeout);
        responseStatus = response.status;
        responseBody = (await response.text()).slice(0, 4096);
        delivered = response.ok;
      } catch (err) {
        error = err instanceof Error ? err.message : 'Unknown error';
      }

      await webhookLogRepo.create({
        id: generateId(),
        payment_id: paymentId,
        merchant_id: merchantId,
        url: webhookUrl,
        request_body: payload as unknown as Record<string, unknown>,
        response_status: responseStatus,
        response_body: responseBody,
        attempt,
        delivered,
        error,
      });

      if (!delivered) {
        throw new Error(`Webhook delivery failed: ${error ?? `HTTP ${responseStatus}`}`);
      }
    },
    {
      connection: redis as any,
      concurrency: 10,
      settings: {
        backoffStrategy: (attemptsMade: number) => {
          const index = Math.min(attemptsMade - 1, WEBHOOK_RETRY_DELAYS_MS.length - 1);
          return WEBHOOK_RETRY_DELAYS_MS[index]!;
        },
      },
    },
  );

  return worker;
}
