import { eq, desc } from 'drizzle-orm';
import { merchants } from '../db/schema/index.js';
import type { Database } from '../config/database.js';

export class MerchantRepository {
  constructor(private db: Database) {}

  async findById(id: string) {
    const rows = await this.db
      .select()
      .from(merchants)
      .where(eq(merchants.id, id))
      .limit(1);
    return rows[0] ?? null;
  }

  async findByEmail(email: string) {
    const rows = await this.db
      .select()
      .from(merchants)
      .where(eq(merchants.email, email))
      .limit(1);
    return rows[0] ?? null;
  }

  async create(data: typeof merchants.$inferInsert) {
    const rows = await this.db.insert(merchants).values(data).returning();
    return rows[0]!;
  }

  async findAll() {
    return this.db
      .select()
      .from(merchants)
      .orderBy(desc(merchants.created_at));
  }

  async update(id: string, data: Partial<typeof merchants.$inferInsert>) {
    const rows = await this.db
      .update(merchants)
      .set({ ...data, updated_at: new Date() })
      .where(eq(merchants.id, id))
      .returning();
    return rows[0] ?? null;
  }
}
