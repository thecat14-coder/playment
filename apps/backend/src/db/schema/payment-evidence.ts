import { pgTable, varchar, integer, text, boolean, timestamp, index, uniqueIndex } from 'drizzle-orm/pg-core';

export const paymentEvidence = pgTable(
  'payment_evidence',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    device_id: varchar('device_id', { length: 26 }).notNull(),
    merchant_id: varchar('merchant_id', { length: 26 }).notNull(),
    raw_notification: text('raw_notification').notNull(),
    amount: integer('amount').notNull(),
    utr: varchar('utr', { length: 50 }),
    rrn: varchar('rrn', { length: 50 }),
    sender_vpa: varchar('sender_vpa', { length: 255 }),
    sender_name: varchar('sender_name', { length: 255 }),
    upi_app: varchar('upi_app', { length: 255 }).notNull(),
    bank: varchar('bank', { length: 255 }),
    notification_package: varchar('notification_package', { length: 255 }).notNull(),
    notification_timestamp: timestamp('notification_timestamp', { withTimezone: true }).notNull(),
    uploaded_at: timestamp('uploaded_at', { withTimezone: true }).notNull().defaultNow(),
    parser_version: varchar('parser_version', { length: 20 }).notNull(),
    nonce: varchar('nonce', { length: 36 }).notNull(),
    signature: varchar('signature', { length: 256 }).notNull(),
    matched: boolean('matched').notNull().default(false),
    matched_payment_id: varchar('matched_payment_id', { length: 26 }),
  },
  (table) => [
    uniqueIndex('idx_evidence_nonce').on(table.nonce),
    index('idx_evidence_merchant_amount').on(table.merchant_id, table.amount, table.matched),
    index('idx_evidence_utr').on(table.utr),
    index('idx_evidence_device_id').on(table.device_id),
  ],
);
