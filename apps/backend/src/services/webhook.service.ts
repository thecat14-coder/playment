import { Queue } from 'bullmq';
import type { WebhookPayload } from '@gateway/shared';
import { WEBHOOK_RETRY_DELAYS_MS } from '@gateway/shared';

const WEBHOOK_QUEUE_NAME = 'webhook-delivery';

export class WebhookService {
  private queue: Queue;

  constructor(redis: unknown) {
    this.queue = new Queue(WEBHOOK_QUEUE_NAME, { connection: redis as any });
  }

  async enqueueDelivery(data: {
    paymentId: string;
    merchantId: string;
    webhookUrl: string;
    webhookSecret: string;
    payload: WebhookPayload;
  }) {
    await this.queue.add('deliver', data, {
      attempts: WEBHOOK_RETRY_DELAYS_MS.length + 1,
      backoff: {
        type: 'custom',
      },
      removeOnComplete: 1000,
      removeOnFail: 5000,
    });
  }

  async enqueueTest(data: {
    merchantId: string;
    webhookUrl: string;
    webhookSecret: string;
  }) {
    const testPayload: WebhookPayload = {
      event: 'payment.success',
      payment_id: 'test_' + Date.now(),
      order_id: 'TEST-ORDER',
      amount: 10000,
      currency: 'INR',
      status: 'success',
      paid_at: new Date().toISOString(),
      confidence: null,
      metadata: null,
    };

    await this.queue.add('deliver', {
      paymentId: testPayload.payment_id,
      merchantId: data.merchantId,
      webhookUrl: data.webhookUrl,
      webhookSecret: data.webhookSecret,
      payload: testPayload,
    }, {
      attempts: 1,
      removeOnComplete: 100,
      removeOnFail: 100,
    });
  }

  getQueue() {
    return this.queue;
  }
}
