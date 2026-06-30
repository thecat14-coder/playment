import { eq, and, desc, count } from 'drizzle-orm';
import { stores, counters } from '../db/schema/index.js';
import type { Database } from '../config/database.js';

export class StoreRepository {
  constructor(private db: Database) {}

  async findStoreById(id: string) {
    const rows = await this.db
      .select()
      .from(stores)
      .where(eq(stores.id, id))
      .limit(1);
    return rows[0] ?? null;
  }

  async findStoresByMerchant(merchantId: string) {
    return this.db
      .select()
      .from(stores)
      .where(eq(stores.merchant_id, merchantId))
      .orderBy(desc(stores.created_at));
  }

  async createStore(data: typeof stores.$inferInsert) {
    const rows = await this.db.insert(stores).values(data).returning();
    return rows[0]!;
  }

  async updateStore(id: string, data: Partial<typeof stores.$inferInsert>) {
    const rows = await this.db
      .update(stores)
      .set(data)
      .where(eq(stores.id, id))
      .returning();
    return rows[0] ?? null;
  }

  async deleteStore(id: string) {
    await this.db.delete(counters).where(eq(counters.store_id, id));
    const rows = await this.db
      .delete(stores)
      .where(eq(stores.id, id))
      .returning();
    return rows[0] ?? null;
  }

  async findCounterById(id: string) {
    const rows = await this.db
      .select()
      .from(counters)
      .where(eq(counters.id, id))
      .limit(1);
    return rows[0] ?? null;
  }

  async findCountersByStore(storeId: string) {
    return this.db
      .select()
      .from(counters)
      .where(eq(counters.store_id, storeId))
      .orderBy(desc(counters.created_at));
  }

  async createCounter(data: typeof counters.$inferInsert) {
    const rows = await this.db.insert(counters).values(data).returning();
    return rows[0]!;
  }
}
