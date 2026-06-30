import { pgTable, varchar, boolean, timestamp, index } from 'drizzle-orm/pg-core';

export const deviceHeartbeats = pgTable(
  'device_heartbeats',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    device_id: varchar('device_id', { length: 26 }).notNull(),
    listener_running: boolean('listener_running').notNull(),
    created_at: timestamp('created_at', { withTimezone: true }).notNull().defaultNow(),
  },
  (table) => [
    index('idx_heartbeats_device_id').on(table.device_id, table.created_at),
  ],
);
