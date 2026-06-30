import { pgTable, varchar, text, boolean, timestamp, index } from 'drizzle-orm/pg-core';

export const notificationLogs = pgTable(
  'notification_logs',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    device_id: varchar('device_id', { length: 26 }).notNull(),
    package_name: varchar('package_name', { length: 255 }).notNull(),
    title: varchar('title', { length: 1024 }),
    body: text('body').notNull(),
    is_payment: boolean('is_payment').notNull().default(false),
    evidence_id: varchar('evidence_id', { length: 26 }),
    received_at: timestamp('received_at', { withTimezone: true }).notNull(),
  },
  (table) => [
    index('idx_notif_logs_device_id').on(table.device_id, table.received_at),
  ],
);
