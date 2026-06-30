/**
 * Cloudflare Pages serves out/404.html for unknown paths like /01ARZ3NDEK...
 * Next.js static export builds a separate 404 page without the checkout app.
 * Copy index.html → 404.html so payment links load the SPA shell.
 */
import { copyFileSync, existsSync } from 'node:fs';
import { dirname, join } from 'node:path';
import { fileURLToPath } from 'node:url';

const outDir = join(dirname(fileURLToPath(import.meta.url)), '..', 'out');
const index = join(outDir, 'index.html');
const notFound = join(outDir, '404.html');

if (!existsSync(index)) {
  console.error('spa-fallback: out/index.html not found — run next build first');
  process.exit(1);
}

copyFileSync(index, notFound);
console.log('spa-fallback: copied index.html → 404.html for payment link routes');
