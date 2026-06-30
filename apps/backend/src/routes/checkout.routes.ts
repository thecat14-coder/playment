import type { FastifyInstance } from 'fastify';
import type { PaymentService } from '../services/payment.service.js';
import type { MerchantRepository } from '../repositories/merchant.repository.js';

export function registerCheckoutRoutes(
  app: FastifyInstance,
  paymentService: PaymentService,
  merchantRepo: MerchantRepository,
) {
  app.get<{ Params: { paymentId: string } }>(
    '/v1/checkout/:paymentId',
    async (request, reply) => {
      const payment = await paymentService.getPayment(request.params.paymentId);
      const merchant = await merchantRepo.findById(payment.merchant_id);

      return reply.send({
        data: {
          payment_id: payment.id,
          amount: payment.amount,
          currency: payment.currency,
          status: payment.status,
          order_id: payment.order_id,
          customer_name: payment.customer_name,
          upi_intent: payment.upi_intent,
          qr_url: payment.qr_url,
          expires_at: payment.expires_at.toISOString(),
          merchant: merchant
            ? {
                name: merchant.name,
                logo_url: merchant.logo_url,
                upi_id: merchant.upi_id,
              }
            : null,
        },
      });
    },
  );
}
