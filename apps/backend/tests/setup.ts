import { vi } from 'vitest';
import { clearEnvCache } from '../src/config/env.js';

process.env.DATABASE_URL = 'postgresql://test:test@localhost:5432/test';
process.env.REDIS_URL = 'redis://localhost:6379';
process.env.JWT_SECRET = 'test-jwt-secret-that-is-at-least-32-chars-long';
process.env.JWT_REFRESH_SECRET = 'test-refresh-secret-that-is-at-least-32-chars-long';
process.env.INTERNAL_API_KEY = 'test-internal-api-key-1234';
process.env.SUPABASE_URL = 'https://test.supabase.co';
process.env.SUPABASE_SERVICE_KEY = 'test-supabase-key';
process.env.BACKEND_URL = 'http://localhost:3001';
process.env.CHECKOUT_URL = 'http://localhost:3002';
process.env.DASHBOARD_URL = 'http://localhost:3003';
process.env.PORT = '3001';
process.env.NODE_ENV = 'test';
process.env.LOG_LEVEL = 'fatal';

clearEnvCache();
