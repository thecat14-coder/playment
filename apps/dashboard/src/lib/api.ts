const BACKEND_URL = process.env.NEXT_PUBLIC_BACKEND_URL || 'http://localhost:3001';

function getToken(): string | null {
  if (typeof window === 'undefined') return null;
  return localStorage.getItem('access_token');
}

async function apiFetch<T>(path: string, options: RequestInit = {}): Promise<T> {
  const token = getToken();
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...((options.headers as Record<string, string>) || {}),
  };
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const res = await fetch(`${BACKEND_URL}${path}`, { ...options, headers });

  if (res.status === 401) {
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
    window.location.href = '/login';
    throw new Error('Unauthorized');
  }

  const json = await res.json();
  if (!res.ok) throw new Error(json.error?.message || 'Request failed');
  return json;
}

export const api = {
  auth: {
    register: (data: { name: string; email: string; password: string; upi_id?: string }) =>
      apiFetch<{ data: { merchant: any; access_token: string; refresh_token: string } }>('/v1/auth/register', {
        method: 'POST',
        body: JSON.stringify(data),
      }),
    login: (data: { email: string; password: string }) =>
      apiFetch<{ data: { merchant: any; access_token: string; refresh_token: string } }>('/v1/auth/login', {
        method: 'POST',
        body: JSON.stringify(data),
      }),
  },
  account: {
    get: () => apiFetch<{ data: any }>('/v1/account'),
    update: (data: any) => apiFetch<{ data: any }>('/v1/account', { method: 'PATCH', body: JSON.stringify(data) }),
  },
  payments: {
    list: (params?: Record<string, string>) => {
      const qs = params ? '?' + new URLSearchParams(params).toString() : '';
      return apiFetch<{ data: any[]; pagination: any }>(`/v1/payments${qs}`);
    },
    get: (id: string) => apiFetch<{ data: any }>(`/v1/payments/${id}`),
  },
  paymentLinks: {
    create: (data: any) =>
      apiFetch<{ data: any }>('/v1/payment-links', { method: 'POST', body: JSON.stringify(data) }),
    list: (params?: Record<string, string>) => {
      const qs = params ? '?' + new URLSearchParams(params).toString() : '';
      return apiFetch<{ data: any[]; pagination: any }>(`/v1/payment-links${qs}`);
    },
    get: (id: string) => apiFetch<{ data: any }>(`/v1/payment-links/${id}`),
  },
  apiKeys: {
    create: (data?: { label?: string }) =>
      apiFetch<{ data: { key: string; prefix: string; label: string | null } }>('/v1/api-keys', {
        method: 'POST',
        body: JSON.stringify(data || {}),
      }),
    list: () => apiFetch<{ data: any[] }>('/v1/api-keys'),
    update: (id: string, data: any) =>
      apiFetch<{ data: any }>(`/v1/api-keys/${id}`, { method: 'PATCH', body: JSON.stringify(data) }),
  },
  webhooks: {
    test: () => apiFetch<{ data: any }>('/v1/webhooks/test', { method: 'POST' }),
    logs: (params?: Record<string, string>) => {
      const qs = params ? '?' + new URLSearchParams(params).toString() : '';
      return apiFetch<{ data: any[]; pagination: any }>(`/v1/webhooks/logs${qs}`);
    },
  },
};
