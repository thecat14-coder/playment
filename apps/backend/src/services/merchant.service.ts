import type { RegisterMerchantInput, UpdateAccountInput } from '@gateway/shared';
import type { MerchantRepository } from '../repositories/merchant.repository.js';
import type { AuthService } from './auth.service.js';
import { generateId } from '../utils/ulid.js';
import { ConflictError, NotFoundError, UnauthorizedError } from '../utils/errors.js';

export class MerchantService {
  constructor(
    private merchantRepo: MerchantRepository,
    private authService: AuthService,
  ) {}

  async register(input: RegisterMerchantInput) {
    const existing = await this.merchantRepo.findByEmail(input.email);
    if (existing) throw new ConflictError('Email already registered');

    const passwordHash = await this.authService.hashPassword(input.password);
    const webhookSecret = this.authService.generateWebhookSecret();

    const merchant = await this.merchantRepo.create({
      id: generateId(),
      name: input.name,
      email: input.email,
      password_hash: passwordHash,
      upi_id: input.upi_id?.trim() || '',
      webhook_secret: webhookSecret,
    });

    const accessToken = this.authService.generateAccessToken(merchant.id);
    const refreshToken = this.authService.generateRefreshToken(merchant.id);

    return {
      merchant: {
        id: merchant.id,
        name: merchant.name,
        email: merchant.email,
        upi_id: merchant.upi_id,
      },
      access_token: accessToken,
      refresh_token: refreshToken,
    };
  }

  async login(email: string, password: string) {
    const merchant = await this.merchantRepo.findByEmail(email);
    if (!merchant) throw new UnauthorizedError('Invalid credentials');

    const valid = await this.authService.verifyPassword(merchant.password_hash, password);
    if (!valid) throw new UnauthorizedError('Invalid credentials');

    const accessToken = this.authService.generateAccessToken(merchant.id);
    const refreshToken = this.authService.generateRefreshToken(merchant.id);

    return {
      merchant: {
        id: merchant.id,
        name: merchant.name,
        email: merchant.email,
        upi_id: merchant.upi_id,
      },
      access_token: accessToken,
      refresh_token: refreshToken,
    };
  }

  async refresh(refreshToken: string) {
    const payload = this.authService.verifyRefreshToken(refreshToken);
    const merchant = await this.merchantRepo.findById(payload.sub);
    if (!merchant) throw new UnauthorizedError('Invalid token');

    const accessToken = this.authService.generateAccessToken(merchant.id);
    const newRefreshToken = this.authService.generateRefreshToken(merchant.id);

    return { access_token: accessToken, refresh_token: newRefreshToken };
  }

  async getAccount(merchantId: string) {
    const merchant = await this.merchantRepo.findById(merchantId);
    if (!merchant) throw new NotFoundError('Merchant not found');

    return {
      id: merchant.id,
      name: merchant.name,
      email: merchant.email,
      upi_id: merchant.upi_id,
      webhook_url: merchant.webhook_url,
      logo_url: merchant.logo_url,
      status: merchant.status,
      created_at: merchant.created_at.toISOString(),
    };
  }

  async updateAccount(merchantId: string, input: UpdateAccountInput) {
    const merchant = await this.merchantRepo.update(merchantId, input);
    if (!merchant) throw new NotFoundError('Merchant not found');

    return {
      id: merchant.id,
      name: merchant.name,
      email: merchant.email,
      upi_id: merchant.upi_id,
      webhook_url: merchant.webhook_url,
      logo_url: merchant.logo_url,
      status: merchant.status,
    };
  }
}
