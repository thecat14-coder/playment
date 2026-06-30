import { eq, and, desc } from 'drizzle-orm';
import { apiKeys } from '../db/schema/index.js';
import type { Database } from '../config/database.js';

export class ApiKeyRepository {
  constructor(private db: Database) {}

  async findByHash(keyHash: string) {
    const rows = await this.db
      .select()
      .from(apiKeys)
      .where(and(eq(apiKeys.key_hash, keyHash), eq(apiKeys.is_active, true)))
      .limit(1);
    return rows[0] ?? null;
  }

  async create(data: typeof apiKeys.$inferInsert) {
    const rows = await this.db.insert(apiKeys).values(data).returning();
    return rows[0]!;
  }

  async listByMerchant(merchantId: string) {
    return this.db
      .select({
        id: apiKeys.id,
        key_prefix: apiKeys.key_prefix,
        label: apiKeys.label,
        is_active: apiKeys.is_active,
        last_used_at: apiKeys.last_used_at,
        created_at: apiKeys.created_at,
      })
      .from(apiKeys)
      .where(eq(apiKeys.merchant_id, merchantId))
      .orderBy(desc(apiKeys.created_at));
  }

  async update(id: string, merchantId: string, data: Partial<typeof apiKeys.$inferInsert>) {
    const rows = await this.db
      .update(apiKeys)
      .set(data)
      .where(and(eq(apiKeys.id, id), eq(apiKeys.merchant_id, merchantId)))
      .returning();
    return rows[0] ?? null;
  }

  async updateLastUsed(id: string) {
    await this.db
      .update(apiKeys)
      .set({ last_used_at: new Date() })
      .where(eq(apiKeys.id, id));
  }
}
