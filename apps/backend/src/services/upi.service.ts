import crypto from 'node:crypto';

export class UpiService {
  buildIntent(params: {
    upiId: string;
    merchantName: string;
    amount: number;
    transactionRef: string;
    note?: string;
  }): string {
    const pa = params.upiId.trim();
    const amountRupees = (params.amount / 100).toFixed(2);
    const tr = this.sanitizeTransactionRef(params.transactionRef);
    const pn = params.merchantName.trim().slice(0, 50);

    const parts = [
      `pa=${this.encodePa(pa)}`,
      `pn=${encodeURIComponent(pn)}`,
      `am=${amountRupees}`,
      `cu=INR`,
      `tr=${encodeURIComponent(tr)}`,
    ];

    const note = params.note?.trim();
    if (note) {
      parts.push(`tn=${encodeURIComponent(note.slice(0, 80))}`);
    }

    return `upi://pay?${parts.join('&')}`;
  }

  private encodePa(vpa: string): string {
    // NPCI examples keep @ literal in pa; encode other special chars only.
    return encodeURIComponent(vpa).replace(/%40/g, '@');
  }

  private sanitizeTransactionRef(ref: string): string {
    const cleaned = ref.replace(/[^A-Za-z0-9-]/g, '').slice(0, 35);
    if (cleaned.length > 0) return cleaned;
    return crypto.randomBytes(8).toString('hex').toUpperCase();
  }
}
