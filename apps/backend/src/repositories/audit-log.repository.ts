import { eq, and, desc, count } from 'drizzle-orm';
import { auditLogs } from '../db/schema/index.js';
import type { Database } from '../config/database.js';

export class AuditLogRepository {
  constructor(private db: Database) {}

  async create(data: typeof auditLogs.$inferInsert) {
    const rows = await this.db.insert(auditLogs).values(data).returning();
    return rows[0]!;
  }

  async findAll(filters: {
    page: number;
    limit: number;
    actor_type?: string;
    resource_type?: string;
    resource_id?: string;
  }) {
    const conditions: ReturnType<typeof eq>[] = [];

    if (filters.actor_type) {
      conditions.push(eq(auditLogs.actor_type, filters.actor_type));
    }
    if (filters.resource_type) {
      conditions.push(eq(auditLogs.resource_type, filters.resource_type));
    }
    if (filters.resource_id) {
      conditions.push(eq(auditLogs.resource_id, filters.resource_id));
    }

    const where = conditions.length > 0 ? and(...conditions) : undefined;
    const offset = (filters.page - 1) * filters.limit;

    const [rows, totalResult] = await Promise.all([
      this.db
        .select()
        .from(auditLogs)
        .where(where)
        .orderBy(desc(auditLogs.created_at))
        .limit(filters.limit)
        .offset(offset),
      this.db
        .select({ total: count() })
        .from(auditLogs)
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

  async findByResource(resourceType: string, resourceId: string) {
    return this.db
      .select()
      .from(auditLogs)
      .where(
        and(
          eq(auditLogs.resource_type, resourceType),
          eq(auditLogs.resource_id, resourceId),
        ),
      )
      .orderBy(desc(auditLogs.created_at));
  }
}
