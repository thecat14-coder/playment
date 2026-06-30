import { notificationLogs } from '../db/schema/index.js';
import type { Database } from '../config/database.js';
import { generateId } from '../utils/ulid.js';

export class NotificationLogRepository {
  constructor(private db: Database) {}

  async logNotification(data: {
    device_id: string;
    package_name: string;
    title?: string;
    body: string;
    is_payment: boolean;
    evidence_id?: string | null;
    received_at: Date;
  }) {
    await this.db.insert(notificationLogs).values({
      id: generateId(),
      device_id: data.device_id,
      package_name: data.package_name,
      title: data.title ?? null,
      body: data.body,
      is_payment: data.is_payment,
      evidence_id: data.evidence_id ?? null,
      received_at: data.received_at,
    });
  }
}
