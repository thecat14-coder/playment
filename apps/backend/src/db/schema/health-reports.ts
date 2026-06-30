import { pgTable, varchar, integer, boolean, timestamp, index } from 'drizzle-orm/pg-core';

export const healthReports = pgTable(
  'health_reports',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    device_id: varchar('device_id', { length: 26 }).notNull(),
    health_score: integer('health_score').notNull(),
    notification_permission: boolean('notification_permission').notNull(),
    battery_optimization_disabled: boolean('battery_optimization_disabled').notNull(),
    foreground_service_running: boolean('foreground_service_running').notNull(),
    listener_running: boolean('listener_running').notNull(),
    internet_connected: boolean('internet_connected').notNull(),
    battery_level: integer('battery_level').notNull(),
    app_version: varchar('app_version', { length: 20 }).notNull(),
    created_at: timestamp('created_at', { withTimezone: true }).notNull().defaultNow(),
  },
  (table) => [
    index('idx_health_device_id').on(table.device_id, table.created_at),
  ],
);
