import { pgTable, varchar, text, boolean, timestamp, index } from 'drizzle-orm/pg-core';
import { merchants } from './merchants.js';

export const stores = pgTable(
  'stores',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    merchant_id: varchar('merchant_id', { length: 26 })
      .notNull()
      .references(() => merchants.id),
    name: varchar('name', { length: 255 }).notNull(),
    address: text('address'),
    upi_id: varchar('upi_id', { length: 255 }),
    is_active: boolean('is_active').notNull().default(true),
    created_at: timestamp('created_at', { withTimezone: true }).notNull().defaultNow(),
  },
  (table) => [
    index('idx_stores_merchant_id').on(table.merchant_id),
  ],
);

export const counters = pgTable(
  'counters',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    store_id: varchar('store_id', { length: 26 })
      .notNull()
      .references(() => stores.id),
    label: varchar('label', { length: 255 }).notNull(),
    is_active: boolean('is_active').notNull().default(true),
    created_at: timestamp('created_at', { withTimezone: true }).notNull().defaultNow(),
  },
  (table) => [
    index('idx_counters_store_id').on(table.store_id),
  ],
);
