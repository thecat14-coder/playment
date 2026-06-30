import { pgTable, varchar, integer, text, timestamp, jsonb, boolean, index } from 'drizzle-orm/pg-core';
import { payments } from './payments.js';
import { merchants } from './merchants.js';

export const webhookLogs = pgTable(
  'webhook_logs',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    payment_id: varchar('payment_id', { length: 26 })
      .notNull()
      .references(() => payments.id),
    merchant_id: varchar('merchant_id', { length: 26 })
      .notNull()
      .references(() => merchants.id),
    url: varchar('url', { length: 2048 }).notNull(),
    request_body: jsonb('request_body').notNull(),
    response_status: integer('response_status'),
    response_body: text('response_body'),
    attempt: integer('attempt').notNull(),
    delivered: boolean('delivered').notNull().default(false),
    error: text('error'),
    created_at: timestamp('created_at', { withTimezone: true }).notNull().defaultNow(),
  },
  (table) => [
    index('idx_webhook_logs_payment_id').on(table.payment_id),
    index('idx_webhook_logs_merchant_id').on(table.merchant_id, table.created_at),
  ],
);
