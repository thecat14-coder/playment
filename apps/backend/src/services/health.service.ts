import {
  HEARTBEAT_STALE_THRESHOLD_SECONDS,
  HEARTBEAT_DEAD_THRESHOLD_SECONDS,
  getHealthLevel,
} from '@gateway/shared';
import type { DeviceRepository } from '../repositories/device.repository.js';
import type { HealthRepository } from '../repositories/health.repository.js';
import type { MerchantRepository } from '../repositories/merchant.repository.js';

export class HealthService {
  constructor(
    private deviceRepo: DeviceRepository,
    private healthRepo: HealthRepository,
    private merchantRepo: MerchantRepository,
  ) {}

  async getMerchantHealthSummary(merchantId: string) {
    const merchant = await this.merchantRepo.findById(merchantId);
    if (!merchant) throw new Error('Merchant not found');

    const devices = await this.deviceRepo.findByMerchantId(merchantId);
    const activeDevices = devices.filter((d) => d.status === 'active');
    const onlineDevices = activeDevices.filter((d) => d.is_online);

    const now = Date.now();
    const deviceSummaries = activeDevices.map((d) => {
      const healthScore = this.computeCombinedHealthScore(d, now);
      return {
        device_id: d.id,
        model: d.model,
        health_score: healthScore,
        health_level: getHealthLevel(healthScore),
        is_online: d.is_online,
        last_heartbeat_at: d.last_heartbeat_at?.toISOString() ?? null,
      };
    });

    const avgHealth = deviceSummaries.length > 0
      ? Math.round(deviceSummaries.reduce((s, d) => s + d.health_score, 0) / deviceSummaries.length)
      : 0;

    return {
      merchant_id: merchantId,
      health_score: avgHealth,
      health_level: getHealthLevel(avgHealth),
      active_devices: activeDevices.length,
      online_devices: onlineDevices.length,
      devices: deviceSummaries,
    };
  }

  async getDeviceHealthDetail(deviceId: string) {
    const [device, latestReport, recentHeartbeats] = await Promise.all([
      this.deviceRepo.findById(deviceId),
      this.healthRepo.getLatestHealthReport(deviceId),
      this.healthRepo.getRecentHeartbeats(
        deviceId,
        new Date(Date.now() - 900000),
      ),
    ]);

    if (!device) return null;

    const now = Date.now();
    const healthScore = this.computeCombinedHealthScore(device, now);

    return {
      device_id: device.id,
      model: device.model,
      manufacturer: device.manufacturer,
      health_score: healthScore,
      health_level: getHealthLevel(healthScore),
      is_online: device.is_online,
      notification_permission: device.notification_permission,
      battery_optimization_disabled: device.battery_optimization_disabled,
      last_heartbeat_at: device.last_heartbeat_at?.toISOString() ?? null,
      last_notification_at: device.last_notification_at?.toISOString() ?? null,
      latest_report: latestReport,
      heartbeat_count_15min: recentHeartbeats.length,
    };
  }

  private computeCombinedHealthScore(
    device: {
      health_score: number;
      notification_permission: boolean;
      battery_optimization_disabled: boolean;
      is_online: boolean;
      last_heartbeat_at: Date | null;
    },
    now: number,
  ): number {
    let score = device.health_score;

    if (!device.notification_permission) score -= 30;
    if (!device.battery_optimization_disabled) score -= 20;

    if (device.last_heartbeat_at) {
      const secondsSinceHeartbeat = (now - device.last_heartbeat_at.getTime()) / 1000;
      if (secondsSinceHeartbeat > HEARTBEAT_DEAD_THRESHOLD_SECONDS) {
        score -= 30;
      } else if (secondsSinceHeartbeat > HEARTBEAT_STALE_THRESHOLD_SECONDS) {
        score -= 15;
      }
    }

    return Math.max(0, Math.min(100, score));
  }

  async getAllMerchantsHealthSummary() {
    throw new Error('Not implemented - admin only');
  }
}
