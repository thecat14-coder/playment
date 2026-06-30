import type { Env } from './env.js';

/** Origins allowed to call the API from a browser (dashboard, checkout, admin). */
export function buildCorsOrigin(env: Env) {
  const allowed = new Set(
    [env.DASHBOARD_URL, env.CHECKOUT_URL, env.ADMIN_URL].map((url) => url.replace(/\/$/, '')),
  );

  return (origin: string | undefined, callback: (err: Error | null, allow: boolean) => void) => {
    // curl / server-side — no Origin header
    if (!origin) {
      callback(null, true);
      return;
    }

    const normalized = origin.replace(/\/$/, '');
    if (allowed.has(normalized)) {
      callback(null, true);
      return;
    }

    // Cloudflare Pages checkout (playment.pages.dev and preview URLs)
    if (/^https:\/\/[\w-]+\.pages\.dev$/.test(normalized)) {
      callback(null, true);
      return;
    }

    callback(null, false);
  };
}
