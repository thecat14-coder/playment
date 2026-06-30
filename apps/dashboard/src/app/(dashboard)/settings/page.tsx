'use client';

import { useEffect, useState } from 'react';
import { api } from '@/lib/api';

export default function SettingsPage() {
  const [account, setAccount] = useState<any>(null);
  const [form, setForm] = useState({ name: '', upi_id: '', webhook_url: '' });
  const [saving, setSaving] = useState(false);
  const [saved, setSaved] = useState(false);

  useEffect(() => {
    api.account.get().then((r) => {
      setAccount(r.data);
      setForm({
        name: r.data.name,
        upi_id: r.data.upi_id,
        webhook_url: r.data.webhook_url || '',
      });
    }).catch(() => {});
  }, []);

  const handleSave = async (e: React.FormEvent) => {
    e.preventDefault();
    setSaving(true);
    setSaved(false);
    try {
      const updateData: any = {};
      if (form.name !== account.name) updateData.name = form.name;
      if (form.upi_id !== account.upi_id) updateData.upi_id = form.upi_id;
      if (form.webhook_url && form.webhook_url !== account.webhook_url) updateData.webhook_url = form.webhook_url;

      if (Object.keys(updateData).length > 0) {
        const res = await api.account.update(updateData);
        setAccount(res.data);
      }
      setSaved(true);
      setTimeout(() => setSaved(false), 3000);
    } catch {
    } finally {
      setSaving(false);
    }
  };

  if (!account) {
    return <div className="flex justify-center py-12"><div className="w-8 h-8 border-2 border-brand-500 border-t-transparent rounded-full animate-spin" /></div>;
  }

  return (
    <div>
      <h1 className="text-2xl font-bold mb-6">Settings</h1>

      <div className="bg-white rounded-xl border border-gray-200 p-6 max-w-xl">
        <form onSubmit={handleSave} className="space-y-5">
          <div>
            <label className="block text-sm font-medium mb-1.5">Business Name</label>
            <input type="text" value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })} required
              className="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-brand-500 focus:border-brand-500 outline-none" />
          </div>

          <div>
            <label className="block text-sm font-medium mb-1.5">Email</label>
            <input type="email" value={account.email} disabled
              className="w-full px-3 py-2 border border-gray-200 rounded-lg text-sm bg-gray-50 text-gray-500" />
          </div>

          <div>
            <label className="block text-sm font-medium mb-1.5">UPI ID</label>
            <input type="text" value={form.upi_id} onChange={(e) => setForm({ ...form, upi_id: e.target.value })} required
              className="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-brand-500 focus:border-brand-500 outline-none" />
            <p className="text-xs text-gray-400 mt-1">All payments will be directed to this UPI ID</p>
          </div>

          <div>
            <label className="block text-sm font-medium mb-1.5">Webhook URL</label>
            <input type="url" value={form.webhook_url} onChange={(e) => setForm({ ...form, webhook_url: e.target.value })} placeholder="https://"
              className="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-brand-500 focus:border-brand-500 outline-none" />
            <p className="text-xs text-gray-400 mt-1">Must use HTTPS. We&apos;ll POST payment updates here.</p>
          </div>

          <div className="flex items-center gap-3 pt-2">
            <button type="submit" disabled={saving}
              className="px-6 py-2 bg-brand-600 text-white rounded-lg text-sm font-medium hover:bg-brand-700 disabled:opacity-50 transition-colors">
              {saving ? 'Saving...' : 'Save Changes'}
            </button>
            {saved && <span className="text-sm text-green-600">Saved!</span>}
          </div>
        </form>
      </div>

      {/* Account Info */}
      <div className="mt-8 bg-white rounded-xl border border-gray-200 p-6 max-w-xl">
        <h2 className="font-semibold mb-4">Account Info</h2>
        <dl className="space-y-3 text-sm">
          <div className="flex justify-between">
            <dt className="text-gray-500">Account ID</dt>
            <dd className="font-mono">{account.id}</dd>
          </div>
          <div className="flex justify-between">
            <dt className="text-gray-500">Status</dt>
            <dd>
              <span className={`inline-flex items-center px-2 py-0.5 rounded-full text-xs font-medium ${account.status === 'active' ? 'bg-green-50 text-green-700' : 'bg-red-50 text-red-700'}`}>
                {account.status}
              </span>
            </dd>
          </div>
          <div className="flex justify-between">
            <dt className="text-gray-500">Member since</dt>
            <dd>{new Date(account.created_at).toLocaleDateString('en-IN', { day: 'numeric', month: 'long', year: 'numeric' })}</dd>
          </div>
        </dl>
      </div>
    </div>
  );
}
