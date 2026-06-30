import { pgTable, varchar, text, timestamp, index } from 'drizzle-orm/pg-core';
import { sql } from 'drizzle-orm';

export const manualReviews = pgTable(
  'manual_reviews',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    payment_id: varchar('payment_id', { length: 26 }).notNull(),
    evidence_id: varchar('evidence_id', { length: 26 }),
    matching_result_id: varchar('matching_result_id', { length: 26 }),
    status: varchar('status', { length: 20 }).notNull().default('pending'),
    reviewer_id: varchar('reviewer_id', { length: 26 }),
    reviewer_type: varchar('reviewer_type', { length: 20 }),
    reason: text('reason'),
    created_at: timestamp('created_at', { withTimezone: true }).notNull().defaultNow(),
    resolved_at: timestamp('resolved_at', { withTimezone: true }),
  },
  (table) => [
    index('idx_reviews_payment_id').on(table.payment_id),
    index('idx_reviews_pending').on(table.status).where(sql`status = 'pending'`),
  ],
);
