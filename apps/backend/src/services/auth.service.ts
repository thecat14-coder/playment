import jwt from 'jsonwebtoken';
import crypto from 'node:crypto';
import argon2 from 'argon2';
import { API_KEY_PREFIX } from '@gateway/shared';
import type { Env } from '../config/env.js';

export class AuthService {
  constructor(private env: Env) {}

  async hashPassword(password: string): Promise<string> {
    return argon2.hash(password, {
      type: argon2.argon2id,
      timeCost: 3,
      memoryCost: 65536,
      parallelism: 4,
    });
  }

  async verifyPassword(hash: string, password: string): Promise<boolean> {
    return argon2.verify(hash, password);
  }

  generateAccessToken(merchantId: string): string {
    return jwt.sign({ sub: merchantId }, this.env.JWT_SECRET, {
      expiresIn: '24h',
    });
  }

  generateRefreshToken(merchantId: string): string {
    return jwt.sign({ sub: merchantId, type: 'refresh' }, this.env.JWT_REFRESH_SECRET, {
      expiresIn: '7d',
    });
  }

  verifyAccessToken(token: string): { sub: string } {
    return jwt.verify(token, this.env.JWT_SECRET) as { sub: string };
  }

  verifyRefreshToken(token: string): { sub: string } {
    const payload = jwt.verify(token, this.env.JWT_REFRESH_SECRET) as {
      sub: string;
      type: string;
    };
    if (payload.type !== 'refresh') {
      throw new Error('Invalid token type');
    }
    return { sub: payload.sub };
  }

  generateApiKey(): { key: string; hash: string; prefix: string } {
    const randomBytes = crypto.randomBytes(40).toString('base64url');
    const key = `${API_KEY_PREFIX}${randomBytes}`;
    const hash = crypto.createHash('sha256').update(key).digest('hex');
    const prefix = key.slice(0, API_KEY_PREFIX.length + 8);
    return { key, hash, prefix };
  }

  hashApiKey(key: string): string {
    return crypto.createHash('sha256').update(key).digest('hex');
  }

  generateDeviceToken(deviceId: string, merchantId: string): string {
    return jwt.sign(
      { sub: merchantId, device_id: deviceId, type: 'device' },
      this.env.DEVICE_JWT_SECRET,
      { expiresIn: '30d' },
    );
  }

  generateDeviceRefreshToken(deviceId: string, merchantId: string): string {
    return jwt.sign(
      { sub: merchantId, device_id: deviceId, type: 'device_refresh' },
      this.env.DEVICE_JWT_REFRESH_SECRET,
      { expiresIn: '30d' },
    );
  }

  verifyDeviceToken(token: string): { sub: string; device_id: string } {
    const payload = jwt.verify(token, this.env.DEVICE_JWT_SECRET) as {
      sub: string;
      device_id: string;
      type: string;
    };
    if (payload.type !== 'device') {
      throw new Error('Invalid token type');
    }
    return { sub: payload.sub, device_id: payload.device_id };
  }

  verifyDeviceRefreshToken(token: string): { sub: string; device_id: string } {
    const payload = jwt.verify(token, this.env.DEVICE_JWT_REFRESH_SECRET) as {
      sub: string;
      device_id: string;
      type: string;
    };
    if (payload.type !== 'device_refresh') {
      throw new Error('Invalid token type');
    }
    return { sub: payload.sub, device_id: payload.device_id };
  }

  generateAdminToken(adminId: string, permissions?: string[]): string {
    return jwt.sign(
      { sub: adminId, role: 'admin', permissions: permissions ?? [] },
      this.env.ADMIN_JWT_SECRET,
      { expiresIn: '4h' },
    );
  }

  generateAdminRefreshToken(adminId: string): string {
    return jwt.sign(
      { sub: adminId, role: 'admin', type: 'refresh' },
      this.env.ADMIN_JWT_REFRESH_SECRET,
      { expiresIn: '24h' },
    );
  }

  verifyAdminToken(token: string): { sub: string; role: string; permissions: string[] } {
    const payload = jwt.verify(token, this.env.ADMIN_JWT_SECRET) as {
      sub: string;
      role: string;
      permissions: string[];
    };
    if (payload.role !== 'admin') {
      throw new Error('Invalid token role');
    }
    return payload;
  }

  generateWebhookSecret(): string {
    return crypto.randomBytes(32).toString('hex');
  }
}
