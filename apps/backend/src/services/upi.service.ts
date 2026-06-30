export class UpiService {
  buildIntent(params: {
    upiId: string;
    merchantName: string;
    amount: number;
    orderId: string;
  }): string {
    const amountRupees = (params.amount / 100).toFixed(2);
    const queryParams = new URLSearchParams({
      pa: params.upiId,
      pn: params.merchantName,
      am: amountRupees,
      cu: 'INR',
      tr: params.orderId,
      tn: params.orderId,
    });
    return `upi://pay?${queryParams.toString()}`;
  }
}
