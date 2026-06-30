import type { PaymentRepository } from '../repositories/payment.repository.js';
import type { PaymentService } from '../services/payment.service.js';

export class ExpiryWorker {
  private intervalId: ReturnType<typeof setInterval> | null = null;

  constructor(
    private paymentRepo: PaymentRepository,
    private paymentService: PaymentService,
    private pollIntervalMs = 10_000,
    private batchSize = 100,
  ) {}

  start() {
    this.intervalId = setInterval(() => this.tick(), this.pollIntervalMs);
    this.tick();
  }

  stop() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
      this.intervalId = null;
    }
  }

  private async tick() {
    try {
      const expired = await this.paymentRepo.findExpiredPending(this.batchSize);
      for (const payment of expired) {
        await this.paymentService.markExpired(payment.id);
      }
    } catch {
      // logged by pino at server level
    }
  }
}
