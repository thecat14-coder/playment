import type { Queue, Worker } from 'bullmq';
import { Queue as BullQueue, Worker as BullWorker } from 'bullmq';
import type Redis from 'ioredis';
import { PaymentStatus } from '@gateway/shared';
import type { EvidenceRepository } from '../repositories/evidence.repository.js';
import type { PaymentRepository } from '../repositories/payment.repository.js';
import type { MatchingService } from '../services/matching.service.js';

export function createMatchingQueue(redis: Redis): Queue {
  return new BullQueue('evidence-matching', { connection: redis as any });
}

export function createMatchingWorker(
  redis: Redis,
  evidenceRepo: EvidenceRepository,
  paymentRepo: PaymentRepository,
  matchingService: MatchingService,
): Worker {
  const worker = new BullWorker(
    'evidence-matching',
    async (job) => {
      const { evidenceId } = job.data as { evidenceId: string };

      const evidence = await evidenceRepo.findById(evidenceId);
      if (!evidence) {
        throw new Error(`Evidence ${evidenceId} not found`);
      }

      const pendingPayments = await paymentRepo.findByMerchantAndAmount(
        evidence.merchant_id,
        evidence.amount,
      );

      const matchingPayments = pendingPayments.filter(
        (p) => p.status === PaymentStatus.PENDING,
      );

      await matchingService.match({
        evidence,
        pendingPayments: matchingPayments.map((p) => ({
          id: p.id,
          amount: p.amount,
          created_at: p.created_at,
          merchant_id: p.merchant_id,
        })),
      });
    },
    {
      connection: redis as any,
      concurrency: 20,
    },
  );

  return worker;
}
