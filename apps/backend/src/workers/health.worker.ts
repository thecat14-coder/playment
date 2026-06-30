import type { Queue, Worker } from 'bullmq';
import { Queue as BullQueue, Worker as BullWorker } from 'bullmq';
import type Redis from 'ioredis';
import type { DeviceRepository } from '../repositories/device.repository.js';
import type { MerchantRepository } from '../repositories/merchant.repository.js';

export function createHealthQueue(redis: Redis): Queue {
  return new BullQueue('health-processing', { connection: redis as any });
}

export function createHealthWorker(
  redis: Redis,
  deviceRepo: DeviceRepository,
  merchantRepo: MerchantRepository,
): Worker {
  const worker = new BullWorker(
    'health-processing',
    async (job) => {
      const { deviceId, healthScore } = job.data as {
        deviceId: string;
        healthScore: number;
      };

      const device = await deviceRepo.findById(deviceId);
      if (!device) return;

      const allMerchantDevices = await deviceRepo.findByMerchantId(device.merchant_id);
      const activeDevices = allMerchantDevices.filter((d) => d.status === 'active');

      if (activeDevices.length === 0) return;

      const avgHealth = Math.round(
        activeDevices.reduce((sum, d) => {
          const score = d.id === deviceId ? healthScore : (d.health_score ?? 0);
          return sum + score;
        }, 0) / activeDevices.length,
      );

      await merchantRepo.update(device.merchant_id, {
        health_score: avgHealth,
      } as any);

      if (healthScore < 40) {
        await redis.publish(
          `pubsub:merchant:${device.merchant_id}`,
          JSON.stringify({
            type: 'health_critical',
            device_id: deviceId,
            health_score: healthScore,
            timestamp: new Date().toISOString(),
          }),
        );
      }
    },
    {
      connection: redis as any,
      concurrency: 10,
    },
  );

  return worker;
}
