import { z } from 'zod';

function normalizePublicUrl(value: unknown, fallback: string): string {
  if (typeof value === 'string' && value.trim()) {
    const trimmed = value.trim();
    if (trimmed.startsWith('http://') || trimmed.startsWith('https://')) {
      return trimmed;
    }
    return `https://${trimmed}`;
  }
  return fallback;
}

function optionalPublicUrl(value: unknown, fallback: string): string {
  if (typeof value === 'string' && value.trim()) {
    return normalizePublicUrl(value, fallback);
  }
  return fallback;
}

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
  BACKEND_URL: z.preprocess(
    (value) =>
      optionalPublicUrl(
        value,
        process.env.RAILWAY_PUBLIC_DOMAIN
          ? `https://${process.env.RAILWAY_PUBLIC_DOMAIN}`
          : 'http://localhost:3001',
      ),
    z.string().url(),
  ),
  CHECKOUT_URL: z.preprocess(
    (value) => optionalPublicUrl(value, 'http://localhost:3002'),
    z.string().url(),
  ),
  DASHBOARD_URL: z.preprocess(
    (value) => optionalPublicUrl(value, 'http://localhost:3003'),
    z.string().url(),
  ),
  ADMIN_URL: z.preprocess(
    (value) => optionalPublicUrl(value, 'http://localhost:3004'),
    z.string().url(),
  ),
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
