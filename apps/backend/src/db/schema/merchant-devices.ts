import { pgTable, varchar, integer, boolean, timestamp, index, uniqueIndex } from 'drizzle-orm/pg-core';
import { sql } from 'drizzle-orm';
import { merchants } from './merchants.js';

export const merchantDevices = pgTable(
  'merchant_devices',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    device_uuid: varchar('device_uuid', { length: 36 }).notNull(),
    merchant_id: varchar('merchant_id', { length: 26 })
      .notNull()
      .references(() => merchants.id),
    store_id: varchar('store_id', { length: 26 }),
    counter_id: varchar('counter_id', { length: 26 }),
    model: varchar('model', { length: 255 }).notNull(),
    manufacturer: varchar('manufacturer', { length: 255 }).notNull(),
    android_version: varchar('android_version', { length: 20 }).notNull(),
    app_version: varchar('app_version', { length: 20 }).notNull(),
    fcm_token: varchar('fcm_token', { length: 512 }),
    status: varchar('status', { length: 20 }).notNull().default('registered'),
    is_online: boolean('is_online').notNull().default(false),
    notification_permission: boolean('notification_permission').notNull().default(false),
    battery_optimization_disabled: boolean('battery_optimization_disabled').notNull().default(false),
    health_score: integer('health_score').notNull().default(0),
    last_heartbeat_at: timestamp('last_heartbeat_at', { withTimezone: true }),
    last_notification_at: timestamp('last_notification_at', { withTimezone: true }),
    device_secret: varchar('device_secret', { length: 64 }).notNull(),
    registered_at: timestamp('registered_at', { withTimezone: true }).notNull().defaultNow(),
  },
  (table) => [
    uniqueIndex('idx_devices_uuid').on(table.device_uuid),
    index('idx_devices_merchant_id').on(table.merchant_id),
    index('idx_devices_active').on(table.merchant_id).where(sql`status = 'active'`),
  ],
);
