import { pgTable, varchar, integer, timestamp, jsonb, index } from 'drizzle-orm/pg-core';

export const matchingResults = pgTable(
  'matching_results',
  {
    id: varchar('id', { length: 26 }).primaryKey(),
    evidence_id: varchar('evidence_id', { length: 26 }).notNull(),
    payment_id: varchar('payment_id', { length: 26 }),
    confidence: integer('confidence').notNull(),
    confidence_level: varchar('confidence_level', { length: 20 }).notNull(),
    score_breakdown: jsonb('score_breakdown').notNull(),
    decision: varchar('decision', { length: 20 }).notNull(),
    matched_at: timestamp('matched_at', { withTimezone: true }).notNull().defaultNow(),
  },
  (table) => [
    index('idx_matching_evidence_id').on(table.evidence_id),
    index('idx_matching_payment_id').on(table.payment_id),
  ],
);
