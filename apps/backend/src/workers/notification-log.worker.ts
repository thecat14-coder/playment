import type { Queue, Worker } from 'bullmq';
import { Queue as BullQueue, Worker as BullWorker } from 'bullmq';
import type Redis from 'ioredis';
import { notificationLogs } from '../db/schema/index.js';
import type { Database } from '../config/database.js';
import { generateId } from '../utils/ulid.js';

export function createNotificationLogQueue(redis: Redis): Queue {
  return new BullQueue('notification-log', { connection: redis as any });
}

export function createNotificationLogWorker(
  redis: Redis,
  db: Database,
): Worker {
  const worker = new BullWorker(
    'notification-log',
    async (job) => {
      const { device_id, package_name, title, body, is_payment, evidence_id, received_at } =
        job.data as {
          device_id: string;
          package_name: string;
          title?: string;
          body: string;
          is_payment: boolean;
          evidence_id?: string;
          received_at: string;
        };

      await db.insert(notificationLogs).values({
        id: generateId(),
        device_id,
        package_name,
        title: title ?? null,
        body,
        is_payment,
        evidence_id: evidence_id ?? null,
        received_at: new Date(received_at),
      });
    },
    {
      connection: redis as any,
      concurrency: 5,
    },
  );

  return worker;
}
