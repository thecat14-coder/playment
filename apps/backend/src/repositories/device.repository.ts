import { eq, and, desc, count, sql, inArray } from 'drizzle-orm';
import { merchantDevices } from '../db/schema/index.js';
import type { Database } from '../config/database.js';

export class DeviceRepository {
  constructor(private db: Database) {}

  async findById(id: string) {
    const rows = await this.db
      .select()
      .from(merchantDevices)
      .where(eq(merchantDevices.id, id))
      .limit(1);
    return rows[0] ?? null;
  }

  async findByUuid(deviceUuid: string) {
    const rows = await this.db
      .select()
      .from(merchantDevices)
      .where(eq(merchantDevices.device_uuid, deviceUuid))
      .limit(1);
    return rows[0] ?? null;
  }

  async findByMerchantId(merchantId: string) {
    return this.db
      .select()
      .from(merchantDevices)
      .where(eq(merchantDevices.merchant_id, merchantId))
      .orderBy(desc(merchantDevices.registered_at));
  }

  async create(data: typeof merchantDevices.$inferInsert) {
    const rows = await this.db.insert(merchantDevices).values(data).returning();
    return rows[0]!;
  }

  async update(id: string, data: Partial<typeof merchantDevices.$inferInsert>) {
    const rows = await this.db
      .update(merchantDevices)
      .set(data)
      .where(eq(merchantDevices.id, id))
      .returning();
    return rows[0] ?? null;
  }

  async updateStatus(id: string, status: string) {
    return this.update(id, { status } as Partial<typeof merchantDevices.$inferInsert>);
  }

  async setOnline(id: string, isOnline: boolean) {
    return this.update(id, { is_online: isOnline } as Partial<typeof merchantDevices.$inferInsert>);
  }

  async listByMerchant(merchantId: string, filters: { page: number; limit: number; status?: string }) {
    const conditions = [eq(merchantDevices.merchant_id, merchantId)];

    if (filters.status) {
      conditions.push(eq(merchantDevices.status, filters.status));
    }

    const where = and(...conditions);
    const offset = (filters.page - 1) * filters.limit;

    const [rows, totalResult] = await Promise.all([
      this.db
        .select()
        .from(merchantDevices)
        .where(where)
        .orderBy(desc(merchantDevices.registered_at))
        .limit(filters.limit)
        .offset(offset),
      this.db
        .select({ total: count() })
        .from(merchantDevices)
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

  async findAll(filters: { page: number; limit: number; status?: string; merchant_id?: string }) {
    const conditions: ReturnType<typeof eq>[] = [];

    if (filters.status) {
      conditions.push(eq(merchantDevices.status, filters.status));
    }
    if (filters.merchant_id) {
      conditions.push(eq(merchantDevices.merchant_id, filters.merchant_id));
    }

    const where = conditions.length > 0 ? and(...conditions) : undefined;
    const offset = (filters.page - 1) * filters.limit;

    const [rows, totalResult] = await Promise.all([
      this.db
        .select()
        .from(merchantDevices)
        .where(where)
        .orderBy(desc(merchantDevices.registered_at))
        .limit(filters.limit)
        .offset(offset),
      this.db
        .select({ total: count() })
        .from(merchantDevices)
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
