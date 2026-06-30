import { pgTable, varchar, integer, text, timestamp, jsonb, index } from 'drizzle-orm/pg-core';
import { sql } from 'drizzle-orm';
import { merchants } from './merchants.js';

export const payments = pgTable(
  'payments',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    merchant_id: varchar('merchant_id', { length: 26 })
      .notNull()
      .references(() => merchants.id),
    store_id: varchar('store_id', { length: 26 }),
    amount: integer('amount').notNull(),
    currency: varchar('currency', { length: 3 }).notNull().default('INR'),
    status: varchar('status', { length: 20 }).notNull().default('pending'),
    order_id: varchar('order_id', { length: 255 }).notNull(),
    customer_name: varchar('customer_name', { length: 255 }),
    customer_email: varchar('customer_email', { length: 255 }),
    customer_phone: varchar('customer_phone', { length: 20 }),
    upi_intent: text('upi_intent').notNull(),
    qr_url: text('qr_url').notNull(),
    payment_link: varchar('payment_link', { length: 2048 }).notNull(),
    metadata: jsonb('metadata'),
    evidence_id: varchar('evidence_id', { length: 26 }),
    confidence: integer('confidence'),
    matched_at: timestamp('matched_at', { withTimezone: true }),
    expires_at: timestamp('expires_at', { withTimezone: true }).notNull(),
    paid_at: timestamp('paid_at', { withTimezone: true }),
    created_at: timestamp('created_at', { withTimezone: true }).notNull().defaultNow(),
  },
  (table) => [
    index('idx_payments_merchant_id').on(table.merchant_id),
    index('idx_payments_status').on(table.status).where(sql`status = 'pending'`),
    index('idx_payments_order_id').on(table.merchant_id, table.order_id),
    index('idx_payments_expires_at').on(table.expires_at).where(sql`status = 'pending'`),
    index('idx_payments_created_at').on(table.merchant_id, table.created_at),
    index('idx_payments_matching').on(table.merchant_id, table.amount, table.status),
  ],
);
