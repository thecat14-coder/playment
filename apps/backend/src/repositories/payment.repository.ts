import { eq, and, sql, lte, desc, count } from 'drizzle-orm';
import { payments } from '../db/schema/index.js';
import type { Database } from '../config/database.js';
import type { PaymentListFilterInput } from '@gateway/shared';

export class PaymentRepository {
  constructor(private db: Database) {}

  async findById(id: string) {
    const rows = await this.db
      .select()
      .from(payments)
      .where(eq(payments.id, id))
      .limit(1);
    return rows[0] ?? null;
  }

  async findByMerchantAndId(merchantId: string, id: string) {
    const rows = await this.db
      .select()
      .from(payments)
      .where(and(eq(payments.id, id), eq(payments.merchant_id, merchantId)))
      .limit(1);
    return rows[0] ?? null;
  }

  async create(data: typeof payments.$inferInsert) {
    const rows = await this.db.insert(payments).values(data).returning();
    return rows[0]!;
  }

  async updateStatus(
    id: string,
    status: string,
    extra?: Partial<typeof payments.$inferInsert>,
  ) {
    const rows = await this.db
      .update(payments)
      .set({ status, ...extra })
      .where(eq(payments.id, id))
      .returning();
    return rows[0] ?? null;
  }

  async listByMerchant(merchantId: string, filters: PaymentListFilterInput) {
    const conditions = [eq(payments.merchant_id, merchantId)];

    if (filters.status) {
      conditions.push(eq(payments.status, filters.status));
    }
    if (filters.from) {
      conditions.push(sql`${payments.created_at} >= ${filters.from}`);
    }
    if (filters.to) {
      conditions.push(sql`${payments.created_at} <= ${filters.to}`);
    }

    const where = and(...conditions);
    const offset = (filters.page - 1) * filters.limit;

    const [rows, totalResult] = await Promise.all([
      this.db
        .select()
        .from(payments)
        .where(where)
        .orderBy(desc(payments.created_at))
        .limit(filters.limit)
        .offset(offset),
      this.db
        .select({ total: count() })
        .from(payments)
        .where(where),
    ]);

    const total = totalResult[0]?.total ?? 0;

    return {
      data: rows,
      pagination: {
        page: filters.page,
        limit: filters.limit,
        total,
        total_pages: Math.ceil(total / filters.limit),
      },
    };
  }

  async findByMerchantAndAmount(merchantId: string, amount: number) {
    return this.db
      .select()
      .from(payments)
      .where(
        and(
          eq(payments.merchant_id, merchantId),
          eq(payments.amount, amount),
        ),
      )
      .orderBy(desc(payments.created_at));
  }

  async findExpiredPending(batchSize: number) {
    return this.db
      .select()
      .from(payments)
      .where(
        and(
          eq(payments.status, 'pending'),
          lte(payments.expires_at, new Date()),
        ),
      )
      .limit(batchSize);
  }
}
