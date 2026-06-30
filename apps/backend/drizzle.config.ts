import { existsSync } from 'node:fs';
import { dirname, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';
import { defineConfig } from 'drizzle-kit';

const root = dirname(fileURLToPath(import.meta.url));
const compiledSchema = resolve(root, 'dist/db/schema/index.js');

// drizzle-kit loads schema via CJS; use compiled JS (dist exists after build / on Railway).
export default defineConfig({
  schema: existsSync(compiledSchema) ? compiledSchema : './src/db/schema/index.ts',
  out: './src/db/migrations',
  dialect: 'postgresql',
  dbCredentials: {
    url: process.env.DATABASE_URL!,
  },
});
