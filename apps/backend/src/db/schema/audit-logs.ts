import { pgTable, varchar, text, timestamp, jsonb, index } from 'drizzle-orm/pg-core';

export const auditLogs = pgTable(
  'audit_logs',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    actor_type: varchar('actor_type', { length: 20 }).notNull(),
    actor_id: varchar('actor_id', { length: 255 }).notNull(),
    action: varchar('action', { length: 255 }).notNull(),
    resource_type: varchar('resource_type', { length: 100 }).notNull(),
    resource_id: varchar('resource_id', { length: 255 }).notNull(),
    details: jsonb('details'),
    ip_address: varchar('ip_address', { length: 45 }),
    created_at: timestamp('created_at', { withTimezone: true }).notNull().defaultNow(),
  },
  (table) => [
    index('idx_audit_resource').on(table.resource_type, table.resource_id),
    index('idx_audit_actor').on(table.actor_type, table.actor_id),
    index('idx_audit_created_at').on(table.created_at),
  ],
);
