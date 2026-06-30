import { pgTable, varchar, integer, timestamp } from 'drizzle-orm/pg-core';

export const merchants = pgTable('merchants', {
  id: varchar('id', { length: 26 }).primaryKey(),
  name: varchar('name', { length: 255 }).notNull(),
  email: varchar('email', { length: 255 }).notNull().unique(),
  password_hash: varchar('password_hash', { length: 255 }).notNull(),
  upi_id: varchar('upi_id', { length: 255 }).notNull(),
  webhook_url: varchar('webhook_url', { length: 2048 }),
  webhook_secret: varchar('webhook_secret', { length: 64 }).notNull(),
  logo_url: varchar('logo_url', { length: 2048 }),
  status: varchar('status', { length: 20 }).notNull().default('active'),
  health_score: integer('health_score').notNull().default(0),
  active_device_count: integer('active_device_count').notNull().default(0),
  created_at: timestamp('created_at', { withTimezone: true }).notNull().defaultNow(),
  updated_at: timestamp('updated_at', { withTimezone: true }).notNull().defaultNow(),
});
