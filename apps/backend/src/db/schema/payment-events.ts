import { pgTable, varchar, timestamp, jsonb, index } from 'drizzle-orm/pg-core';
import { payments } from './payments.js';

export const paymentEvents = pgTable(
  'payment_events',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    payment_id: varchar('payment_id', { length: 26 })
      .notNull()
      .references(() => payments.id),
    type: varchar('type', { length: 50 }).notNull(),
    payload: jsonb('payload').notNull(),
    source: varchar('source', { length: 50 }).notNull(),
    created_at: timestamp('created_at', { withTimezone: true }).notNull().defaultNow(),
  },
  (table) => [
    index('idx_payment_events_payment_id').on(table.payment_id),
  ],
);
