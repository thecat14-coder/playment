import { eq, and, desc, count, sql } from 'drizzle-orm';
import { healthReports, deviceHeartbeats } from '../db/schema/index.js';
import type { Database } from '../config/database.js';

export class HealthRepository {
  constructor(private db: Database) {}

  async createHealthReport(data: typeof healthReports.$inferInsert) {
    const rows = await this.db.insert(healthReports).values(data).returning();
    return rows[0]!;
  }

  async getLatestHealthReport(deviceId: string) {
    const rows = await this.db
      .select()
      .from(healthReports)
      .where(eq(healthReports.device_id, deviceId))
      .orderBy(desc(healthReports.created_at))
      .limit(1);
    return rows[0] ?? null;
  }

  async getHealthReports(deviceId: string, limit = 20) {
    return this.db
      .select()
      .from(healthReports)
      .where(eq(healthReports.device_id, deviceId))
      .orderBy(desc(healthReports.created_at))
      .limit(limit);
  }

  async createHeartbeat(data: typeof deviceHeartbeats.$inferInsert) {
    const rows = await this.db.insert(deviceHeartbeats).values(data).returning();
    return rows[0]!;
  }

  async getRecentHeartbeats(deviceId: string, since: Date) {
    return this.db
      .select()
      .from(deviceHeartbeats)
      .where(
        and(
          eq(deviceHeartbeats.device_id, deviceId),
          sql`${deviceHeartbeats.created_at} >= ${since}`,
        ),
      )
      .orderBy(desc(deviceHeartbeats.created_at));
  }
}
