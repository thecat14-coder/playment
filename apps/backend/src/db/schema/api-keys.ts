import { pgTable, varchar, timestamp, boolean, index } from 'drizzle-orm/pg-core';
import { sql } from 'drizzle-orm';
import { merchants } from './merchants.js';

export const apiKeys = pgTable(
  'api_keys',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    merchant_id: varchar('merchant_id', { length: 26 })
      .notNull()
      .references(() => merchants.id),
    key_hash: varchar('key_hash', { length: 255 }).notNull().unique(),
    key_prefix: varchar('key_prefix', { length: 12 }).notNull(),
    label: varchar('label', { length: 255 }),
    last_used_at: timestamp('last_used_at', { withTimezone: true }),
    is_active: boolean('is_active').notNull().default(true),
    created_at: timestamp('created_at', { withTimezone: true }).notNull().defaultNow(),
  },
  (table) => [
    index('idx_api_keys_key_hash').on(table.key_hash).where(sql`is_active = true`),
    index('idx_api_keys_merchant_id').on(table.merchant_id),
  ],
);
