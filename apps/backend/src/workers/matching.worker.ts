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
      await matchingService.runMatchingForEvidence(evidenceId);
    },
    {
      connection: redis as any,
      concurrency: 20,
    },
  );

  return worker;
}
