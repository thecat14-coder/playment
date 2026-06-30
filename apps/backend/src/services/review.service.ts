import { PaymentStatus } from '@gateway/shared';
import type { ReviewRepository } from '../repositories/review.repository.js';
import type { PaymentRepository } from '../repositories/payment.repository.js';
import type { PaymentService } from './payment.service.js';
import type { WebhookService } from './webhook.service.js';
import { generateId } from '../utils/ulid.js';
import { NotFoundError, ConflictError } from '../utils/errors.js';

export class ReviewService {
  constructor(
    private reviewRepo: ReviewRepository,
    private paymentRepo: PaymentRepository,
    private paymentService: PaymentService,
    private webhookService: WebhookService,
  ) {}

  async createReview(paymentId: string, evidenceId: string, matchingResultId: string) {
    const existing = await this.reviewRepo.findByPaymentId(paymentId);
    if (existing) return existing;

    return this.reviewRepo.create({
      id: generateId(),
      payment_id: paymentId,
      evidence_id: evidenceId,
      matching_result_id: matchingResultId,
      status: 'pending',
    });
  }

  async getReview(id: string) {
    return this.reviewRepo.findById(id);
  }

  async listPendingReviews(merchantId: string, filters: { page: number; limit: number }) {
    return this.reviewRepo.listPendingByMerchant(merchantId, filters);
  }

  async listAllPendingReviews(filters: { page: number; limit: number }) {
    return this.reviewRepo.listAllPending(filters);
  }

  async approveReview(
    reviewId: string,
    reviewerId: string,
    reviewerType: 'merchant' | 'admin',
    reason?: string,
  ) {
    const review = await this.reviewRepo.findById(reviewId);
    if (!review) throw new NotFoundError('Review not found');
    if (review.status !== 'pending') throw new ConflictError('Review is already resolved');

    await this.reviewRepo.resolve(reviewId, 'approved', reviewerId, reviewerType, reason);
    await this.paymentService.markSuccess(review.payment_id, {
      review_id: reviewId,
      reviewer_type: reviewerType,
      approved_at: new Date().toISOString(),
    });
  }

  async rejectReview(
    reviewId: string,
    reviewerId: string,
    reviewerType: 'merchant' | 'admin',
    reason?: string,
  ) {
    const review = await this.reviewRepo.findById(reviewId);
    if (!review) throw new NotFoundError('Review not found');
    if (review.status !== 'pending') throw new ConflictError('Review is already resolved');

    await this.reviewRepo.resolve(reviewId, 'rejected', reviewerId, reviewerType, reason);
    await this.paymentService.markFailed(review.payment_id, reason ?? 'Rejected via manual review');
  }

  async forceFailReview(
    reviewId: string,
    adminId: string,
    reason?: string,
  ) {
    const review = await this.reviewRepo.findById(reviewId);
    if (!review) throw new NotFoundError('Review not found');
    if (review.status !== 'pending') throw new ConflictError('Review is already resolved');

    await this.reviewRepo.resolve(reviewId, 'force_failed', adminId, 'admin', reason);
    await this.paymentService.markFailed(review.payment_id, reason ?? 'Force failed by admin');
  }
}
