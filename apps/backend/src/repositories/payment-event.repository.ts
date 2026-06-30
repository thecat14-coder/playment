import { eq, desc } from 'drizzle-orm';
import { paymentEvents } from '../db/schema/index.js';
import type { Database } from '../config/database.js';

export class PaymentEventRepository {
  constructor(private db: Database) {}

  async create(data: typeof paymentEvents.$inferInsert) {
    const rows = await this.db.insert(paymentEvents).values(data).returning();
    return rows[0]!;
  }

  async findByPaymentId(paymentId: string) {
    return this.db
      .select()
      .from(paymentEvents)
      .where(eq(paymentEvents.payment_id, paymentId))
      .orderBy(desc(paymentEvents.created_at));
  }
}
