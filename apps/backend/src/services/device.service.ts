import crypto from 'node:crypto';
import type Redis from 'ioredis';
import type { DeviceRegisterInput } from '@gateway/shared';
import type { DeviceRepository } from '../repositories/device.repository.js';
import type { HealthRepository } from '../repositories/health.repository.js';
import type { AuthService } from './auth.service.js';
import { generateId } from '../utils/ulid.js';
import { NotFoundError, ConflictError, UnauthorizedError } from '../utils/errors.js';

export class DeviceService {
  constructor(
    private deviceRepo: DeviceRepository,
    private healthRepo: HealthRepository,
    private authService: AuthService,
    private redis: Redis,
  ) {}

  async register(merchantId: string, input: DeviceRegisterInput) {
    const existing = await this.deviceRepo.findByUuid(input.device_uuid);
    if (existing) {
      if (existing.merchant_id !== merchantId) {
        throw new ConflictError('Device already registered to another merchant');
      }
      const deviceJwt = this.authService.generateDeviceToken(existing.id, existing.merchant_id);
      const deviceRefreshJwt = this.authService.generateDeviceRefreshToken(existing.id, existing.merchant_id);
      return {
        device_id: existing.id,
        device_secret: existing.device_secret,
        status: existing.status,
        token: deviceJwt,
        refresh_token: deviceRefreshJwt,
      };
    }

    const deviceId = generateId();
    const deviceSecret = crypto.randomBytes(32).toString('hex');

    const device = await this.deviceRepo.create({
      id: deviceId,
      device_uuid: input.device_uuid,
      merchant_id: merchantId,
      model: input.model,
      manufacturer: input.manufacturer,
      android_version: input.android_version,
      app_version: input.app_version,
      fcm_token: input.fcm_token ?? null,
      device_secret: deviceSecret,
      status: 'active',
      health_score: 70,
    });

    const deviceJwt = this.authService.generateDeviceToken(deviceId, merchantId);
    const deviceRefreshJwt = this.authService.generateDeviceRefreshToken(deviceId, merchantId);

    return {
      device_id: device.id,
      device_secret: deviceSecret,
      status: device.status,
      token: deviceJwt,
      refresh_token: deviceRefreshJwt,
    };
  }

  async refreshToken(refreshToken: string) {
    let payload: { sub: string; device_id: string };
    try {
      payload = this.authService.verifyDeviceRefreshToken(refreshToken);
    } catch {
      throw new UnauthorizedError('Invalid or expired device refresh token');
    }
    const device = await this.deviceRepo.findById(payload.device_id);
    if (!device) {
      throw new NotFoundError('Device not found');
    }
    if (device.status === 'suspended' || device.status === 'deregistered') {
      throw new ConflictError('Device is suspended or deregistered');
    }
    if (device.merchant_id !== payload.sub) {
      throw new ConflictError('Device does not belong to this merchant');
    }

    return {
      token: this.authService.generateDeviceToken(device.id, device.merchant_id),
      refresh_token: this.authService.generateDeviceRefreshToken(device.id, device.merchant_id),
    };
  }

  async handleHeartbeat(deviceId: string, listenerRunning: boolean) {
    const device = await this.deviceRepo.findById(deviceId);
    if (!device) throw new NotFoundError('Device not found');

    const now = new Date();
    const heartbeatId = generateId();

    await Promise.all([
      this.healthRepo.createHeartbeat({
        id: heartbeatId,
        device_id: deviceId,
        listener_running: listenerRunning,
      }),
      this.deviceRepo.update(deviceId, {
        is_online: true,
        last_heartbeat_at: now,
      } as any),
      this.redis.setex(`device:online:${deviceId}`, 120, '1'),
      this.redis.zadd('device:heartbeat', now.getTime(), deviceId),
    ]);
  }

  async handleHealthReport(
    deviceId: string,
    report: {
      notification_permission: boolean;
      battery_optimization_disabled: boolean;
      foreground_service_running: boolean;
      listener_running: boolean;
      internet_connected: boolean;
      battery_level: number;
      app_version: string;
    },
  ) {
    const device = await this.deviceRepo.findById(deviceId);
    if (!device) throw new NotFoundError('Device not found');

    const healthScore = this.computeHealthScore(report);
    const reportId = generateId();

    await Promise.all([
      this.healthRepo.createHealthReport({
        id: reportId,
        device_id: deviceId,
        health_score: healthScore,
        notification_permission: report.notification_permission,
        battery_optimization_disabled: report.battery_optimization_disabled,
        foreground_service_running: report.foreground_service_running,
        listener_running: report.listener_running,
        internet_connected: report.internet_connected,
        battery_level: report.battery_level,
        app_version: report.app_version,
      }),
      this.deviceRepo.update(deviceId, {
        health_score: healthScore,
        notification_permission: report.notification_permission,
        battery_optimization_disabled: report.battery_optimization_disabled,
      } as any),
    ]);

    return healthScore;
  }

  private computeHealthScore(report: {
    notification_permission: boolean;
    battery_optimization_disabled: boolean;
    foreground_service_running: boolean;
    listener_running: boolean;
    internet_connected: boolean;
    battery_level: number;
    app_version: string;
  }): number {
    let health = 100;

    if (!report.notification_permission) health -= 30;
    if (!report.foreground_service_running) health -= 25;
    if (!report.internet_connected) health -= 30;
    if (!report.battery_optimization_disabled) health -= 20;
    if (report.battery_level < 15) health -= 5;

    return Math.max(0, health);
  }

  async setOnlineStatus(deviceId: string, isOnline: boolean) {
    const device = await this.deviceRepo.findById(deviceId);
    if (!device) throw new NotFoundError('Device not found');

    await this.deviceRepo.setOnline(deviceId, isOnline);

    if (!isOnline) {
      await this.redis.del(`device:online:${deviceId}`);
    } else {
      await this.redis.setex(`device:online:${deviceId}`, 120, '1');
    }
  }

  async getDevice(id: string) {
    return this.deviceRepo.findById(id);
  }

  async listDevices(merchantId: string, filters: { page: number; limit: number; status?: string }) {
    return this.deviceRepo.listByMerchant(merchantId, filters);
  }

  async assignDevice(deviceId: string, storeId: string | null, counterId?: string | null) {
    const device = await this.deviceRepo.findById(deviceId);
    if (!device) throw new NotFoundError('Device not found');

    return this.deviceRepo.update(deviceId, {
      store_id: storeId,
      counter_id: counterId ?? null,
    } as any);
  }

  async deregister(deviceId: string) {
    const device = await this.deviceRepo.findById(deviceId);
    if (!device) throw new NotFoundError('Device not found');

    await this.deviceRepo.updateStatus(deviceId, 'deregistered');
    await this.redis.del(`device:online:${deviceId}`);
  }
}
