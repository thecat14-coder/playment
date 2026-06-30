import { eq, and, desc, count, sql } from 'drizzle-orm';
import { paymentEvidence } from '../db/schema/index.js';
import type { Database } from '../config/database.js';

export class EvidenceRepository {
  constructor(private db: Database) {}

  async findById(id: string) {
    const rows = await this.db
      .select()
      .from(paymentEvidence)
      .where(eq(paymentEvidence.id, id))
      .limit(1);
    return rows[0] ?? null;
  }

  async findByNonce(nonce: string) {
    const rows = await this.db
      .select()
      .from(paymentEvidence)
      .where(eq(paymentEvidence.nonce, nonce))
      .limit(1);
    return rows[0] ?? null;
  }

  async findByPaymentId(paymentId: string) {
    return this.db
      .select()
      .from(paymentEvidence)
      .where(eq(paymentEvidence.matched_payment_id, paymentId))
      .orderBy(desc(paymentEvidence.uploaded_at));
  }

  async create(data: typeof paymentEvidence.$inferInsert) {
    const rows = await this.db.insert(paymentEvidence).values(data).returning();
    return rows[0]!;
  }

  async markMatched(id: string, paymentId: string) {
    const rows = await this.db
      .update(paymentEvidence)
      .set({ matched: true, matched_payment_id: paymentId })
      .where(eq(paymentEvidence.id, id))
      .returning();
    return rows[0] ?? null;
  }

  async listByMerchant(
    merchantId: string,
    filters: { page: number; limit: number; matched?: boolean; from?: Date; to?: Date },
  ) {
    const conditions = [eq(paymentEvidence.merchant_id, merchantId)];

    if (filters.matched !== undefined) {
      conditions.push(eq(paymentEvidence.matched, filters.matched));
    }
    if (filters.from) {
      conditions.push(sql`${paymentEvidence.uploaded_at} >= ${filters.from}`);
    }
    if (filters.to) {
      conditions.push(sql`${paymentEvidence.uploaded_at} <= ${filters.to}`);
    }

    const where = and(...conditions);
    const offset = (filters.page - 1) * filters.limit;

    const [rows, totalResult] = await Promise.all([
      this.db
        .select()
        .from(paymentEvidence)
        .where(where)
        .orderBy(desc(paymentEvidence.uploaded_at))
        .limit(filters.limit)
        .offset(offset),
      this.db
        .select({ total: count() })
        .from(paymentEvidence)
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

  async findUnmatchedByMerchantAndAmount(merchantId: string, amount: number) {
    return this.db
      .select()
      .from(paymentEvidence)
      .where(
        and(
          eq(paymentEvidence.merchant_id, merchantId),
          eq(paymentEvidence.amount, amount),
          eq(paymentEvidence.matched, false),
        ),
      )
      .orderBy(desc(paymentEvidence.uploaded_at));
  }

  async findAll(filters: { page: number; limit: number; merchant_id?: string; matched?: boolean }) {
    const conditions: ReturnType<typeof eq>[] = [];

    if (filters.merchant_id) {
      conditions.push(eq(paymentEvidence.merchant_id, filters.merchant_id));
    }
    if (filters.matched !== undefined) {
      conditions.push(eq(paymentEvidence.matched, filters.matched));
    }

    const where = conditions.length > 0 ? and(...conditions) : undefined;
    const offset = (filters.page - 1) * filters.limit;

    const [rows, totalResult] = await Promise.all([
      this.db
        .select()
        .from(paymentEvidence)
        .where(where)
        .orderBy(desc(paymentEvidence.uploaded_at))
        .limit(filters.limit)
        .offset(offset),
      this.db
        .select({ total: count() })
        .from(paymentEvidence)
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
}
