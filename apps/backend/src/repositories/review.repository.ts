import { eq, and, desc, count, sql, isNull } from 'drizzle-orm';
import { manualReviews, payments } from '../db/schema/index.js';
import type { Database } from '../config/database.js';

export class ReviewRepository {
  constructor(private db: Database) {}

  async findById(id: string) {
    const rows = await this.db
      .select()
      .from(manualReviews)
      .where(eq(manualReviews.id, id))
      .limit(1);
    return rows[0] ?? null;
  }

  async findByPaymentId(paymentId: string) {
    const rows = await this.db
      .select()
      .from(manualReviews)
      .where(eq(manualReviews.payment_id, paymentId))
      .limit(1);
    return rows[0] ?? null;
  }

  async create(data: typeof manualReviews.$inferInsert) {
    const rows = await this.db.insert(manualReviews).values(data).returning();
    return rows[0]!;
  }

  async resolve(
    id: string,
    status: string,
    reviewerId: string,
    reviewerType: string,
    reason?: string | null,
  ) {
    const rows = await this.db
      .update(manualReviews)
      .set({
        status,
        reviewer_id: reviewerId,
        reviewer_type: reviewerType,
        reason: reason ?? null,
        resolved_at: new Date(),
      })
      .where(eq(manualReviews.id, id))
      .returning();
    return rows[0] ?? null;
  }

  async listPendingByMerchant(
    merchantId: string,
    filters: { page: number; limit: number },
  ) {
    const offset = (filters.page - 1) * filters.limit;

    const [rows, totalResult] = await Promise.all([
      this.db
        .select()
        .from(manualReviews)
        .innerJoin(payments, eq(manualReviews.payment_id, payments.id))
        .where(
          and(
            eq(payments.merchant_id, merchantId),
            eq(manualReviews.status, 'pending'),
          ),
        )
        .orderBy(desc(manualReviews.created_at))
        .limit(filters.limit)
        .offset(offset),
      this.db
        .select({ total: count() })
        .from(manualReviews)
        .innerJoin(payments, eq(manualReviews.payment_id, payments.id))
        .where(
          and(
            eq(payments.merchant_id, merchantId),
            eq(manualReviews.status, 'pending'),
          ),
        ),
    ]);

    const total = totalResult[0]?.total ?? 0;

    return {
      data: rows.map((r) => ({ ...r.manual_reviews, payment: r.payments })),
      pagination: {
        page: filters.page,
        limit: filters.limit,
        total,
        total_pages: Math.ceil(total / filters.limit),
      },
    };
  }

  async listAllPending(filters: { page: number; limit: number }) {
    const offset = (filters.page - 1) * filters.limit;

    const [rows, totalResult] = await Promise.all([
      this.db
        .select()
        .from(manualReviews)
        .innerJoin(payments, eq(manualReviews.payment_id, payments.id))
        .where(eq(manualReviews.status, 'pending'))
        .orderBy(desc(manualReviews.created_at))
        .limit(filters.limit)
        .offset(offset),
      this.db
        .select({ total: count() })
        .from(manualReviews)
        .innerJoin(payments, eq(manualReviews.payment_id, payments.id))
        .where(eq(manualReviews.status, 'pending')),
    ]);

    const total = totalResult[0]?.total ?? 0;

    return {
      data: rows.map((r) => ({ ...r.manual_reviews, payment: r.payments })),
      pagination: {
        page: filters.page,
        limit: filters.limit,
        total,
        total_pages: Math.ceil(total / filters.limit),
      },
    };
  }
}
