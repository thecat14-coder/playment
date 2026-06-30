const BACKEND_URL = process.env.NEXT_PUBLIC_BACKEND_URL || 'http://localhost:3001';

function getAdminToken(): string | null {
  if (typeof window === 'undefined') return null;
  return localStorage.getItem('admin_token');
}

async function apiFetch<T>(path: string, options: RequestInit = {}): Promise<T> {
  const token = getAdminToken();
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...((options.headers as Record<string, string>) || {}),
  };
  if (token) headers['Authorization'] = `Bearer ${token}`;

  const res = await fetch(`${BACKEND_URL}${path}`, { ...options, headers });

  if (res.status === 401) {
    localStorage.removeItem('admin_token');
    window.location.href = '/login';
    throw new Error('Unauthorized');
  }

  const json = await res.json();
  if (!res.ok) throw new Error(json.error?.message || 'Request failed');
  return json;
}

export const adminApi = {
  merchants: {
    list: () => apiFetch<{ data: any[] }>('/admin/merchants'),
    suspend: (id: string) =>
      apiFetch<{ data: any }>(`/admin/merchants/${id}/suspend`, { method: 'POST' }),
  },
  devices: {
    list: (params?: Record<string, string>) => {
      const qs = params ? '?' + new URLSearchParams(params).toString() : '';
      return apiFetch<{ data: any[]; pagination: any }>(`/admin/devices${qs}`);
    },
    disable: (id: string) =>
      apiFetch<{ data: any }>(`/admin/devices/${id}/disable`, { method: 'POST' }),
  },
  evidence: {
    list: (params?: Record<string, string>) => {
      const qs = params ? '?' + new URLSearchParams(params).toString() : '';
      return apiFetch<{ data: any[]; pagination: any }>(`/admin/evidence${qs}`);
    },
  },
  reviews: {
    list: (params?: Record<string, string>) => {
      const qs = params ? '?' + new URLSearchParams(params).toString() : '';
      return apiFetch<{ data: any[]; pagination: any }>(`/admin/reviews${qs}`);
    },
    approve: (id: string, reason?: string) =>
      apiFetch<{ data: any }>(`/admin/reviews/${id}/approve`, { method: 'POST', body: JSON.stringify({ reason }) }),
    reject: (id: string, reason?: string) =>
      apiFetch<{ data: any }>(`/admin/reviews/${id}/reject`, { method: 'POST', body: JSON.stringify({ reason }) }),
    forceFail: (id: string, reason?: string) =>
      apiFetch<{ data: any }>(`/admin/reviews/${id}/force-fail`, { method: 'POST', body: JSON.stringify({ reason }) }),
  },
  health: {
    overview: (params?: Record<string, string>) => {
      const qs = params ? '?' + new URLSearchParams(params).toString() : '';
      return apiFetch<{ data: any }>(`/admin/health${qs}`);
    },
  },
  audit: {
    list: (params?: Record<string, string>) => {
      const qs = params ? '?' + new URLSearchParams(params).toString() : '';
      return apiFetch<{ data: any[]; pagination: any }>(`/admin/audit-logs${qs}`);
    },
  },
  matching: {
    retry: (paymentId: string) =>
      apiFetch<{ data: any }>(`/admin/matching/retry/${paymentId}`, { method: 'POST' }),
  },
};

export function adminLogin(token: string) {
  localStorage.setItem('admin_token', token);
}

export function adminLogout() {
  localStorage.removeItem('admin_token');
  window.location.href = '/login';
}
