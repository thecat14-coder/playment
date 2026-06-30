import { eq, desc, and } from 'drizzle-orm';
import { matchingResults } from '../db/schema/index.js';
import type { Database } from '../config/database.js';

export class MatchingRepository {
  constructor(private db: Database) {}

  async findById(id: string) {
    const rows = await this.db
      .select()
      .from(matchingResults)
      .where(eq(matchingResults.id, id))
      .limit(1);
    return rows[0] ?? null;
  }

  async findByEvidenceId(evidenceId: string) {
    return this.db
      .select()
      .from(matchingResults)
      .where(eq(matchingResults.evidence_id, evidenceId))
      .orderBy(desc(matchingResults.matched_at));
  }

  async findByPaymentId(paymentId: string) {
    return this.db
      .select()
      .from(matchingResults)
      .where(eq(matchingResults.payment_id, paymentId))
      .orderBy(desc(matchingResults.matched_at));
  }

  async create(data: typeof matchingResults.$inferInsert) {
    const rows = await this.db.insert(matchingResults).values(data).returning();
    return rows[0]!;
  }
}
