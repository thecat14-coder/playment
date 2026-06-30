import { eq, desc, and, count } from 'drizzle-orm';
import { webhookLogs } from '../db/schema/index.js';
import type { Database } from '../config/database.js';
import type { PaginationInput } from '@gateway/shared';

export class WebhookLogRepository {
  constructor(private db: Database) {}

  async create(data: typeof webhookLogs.$inferInsert) {
    const rows = await this.db.insert(webhookLogs).values(data).returning();
    return rows[0]!;
  }

  async listByMerchant(merchantId: string, pagination: PaginationInput) {
    const offset = (pagination.page - 1) * pagination.limit;

    const where = eq(webhookLogs.merchant_id, merchantId);

    const [rows, totalResult] = await Promise.all([
      this.db
        .select()
        .from(webhookLogs)
        .where(where)
        .orderBy(desc(webhookLogs.created_at))
        .limit(pagination.limit)
        .offset(offset),
      this.db
        .select({ total: count() })
        .from(webhookLogs)
        .where(where),
    ]);

    return {
      data: rows,
      pagination: {
        page: pagination.page,
        limit: pagination.limit,
        total: totalResult[0]?.total ?? 0,
        total_pages: Math.ceil((totalResult[0]?.total ?? 0) / pagination.limit),
      },
    };
  }

  async findByPaymentId(paymentId: string) {
    return this.db
      .select()
      .from(webhookLogs)
      .where(eq(webhookLogs.payment_id, paymentId))
      .orderBy(desc(webhookLogs.created_at));
  }
}
