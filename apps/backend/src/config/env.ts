import { z } from 'zod';

const envSchema = z.object({
  DATABASE_URL: z.string().url(),
  REDIS_URL: z.string().url(),
  JWT_SECRET: z.string().min(32),
  JWT_REFRESH_SECRET: z.string().min(32),
  DEVICE_JWT_SECRET: z.string().min(32),
  DEVICE_JWT_REFRESH_SECRET: z.string().min(32),
  ADMIN_JWT_SECRET: z.string().min(32),
  ADMIN_JWT_REFRESH_SECRET: z.string().min(32),
  INTERNAL_API_KEY: z.string().min(16),
  SUPABASE_URL: z.string().url().optional(),
  SUPABASE_SERVICE_KEY: z.string().min(1).optional(),
  BACKEND_URL: z.string().url().default('http://localhost:3001'),
  CHECKOUT_URL: z.string().url().default('http://localhost:3002'),
  DASHBOARD_URL: z.string().url().default('http://localhost:3003'),
  ADMIN_URL: z.string().url().default('http://localhost:3004'),
  PORT: z.coerce.number().default(3001),
  NODE_ENV: z.enum(['development', 'production', 'test']).default('development'),
  LOG_LEVEL: z.enum(['fatal', 'error', 'warn', 'info', 'debug', 'trace']).default('info'),
});

export type Env = z.infer<typeof envSchema>;

let cached: Env | null = null;

export function clearEnvCache(): void {
  cached = null;
}

export function getEnv(): Env {
  if (cached) return cached;
  const result = envSchema.safeParse(process.env);
  if (!result.success) {
    const formatted = result.error.issues
      .map((i) => `  ${i.path.join('.')}: ${i.message}`)
      .join('\n');
    throw new Error(`Invalid environment variables:\n${formatted}`);
  }
  cached = result.data;
  return cached;
}
