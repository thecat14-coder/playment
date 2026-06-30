import {
  PaymentStatus,
  MatchingDecision,
  ConfidenceLevel,
  CONFIDENCE_THRESHOLDS,
  MATCHING_SCORES,
  getConfidenceLevel,
} from '@gateway/shared';
import type { PaymentEvidence } from '@gateway/shared';
import type { MatchingRepository } from '../repositories/matching.repository.js';
import type { EvidenceRepository } from '../repositories/evidence.repository.js';
import type { PaymentRepository } from '../repositories/payment.repository.js';
import type { DeviceRepository } from '../repositories/device.repository.js';
import type { ReviewService } from './review.service.js';
import type { PaymentService } from './payment.service.js';
import { generateId } from '../utils/ulid.js';

export interface ScoreBreakdown {
  time_proximity: number;
  utr_uniqueness: number;
  single_pending_match: number;
  device_trust: number;
  evidence_quality: number;
  total: number;
}

export interface MatchingInput {
  evidence: PaymentEvidence;
  pendingPayments: Array<{ id: string; amount: number; created_at: Date; merchant_id: string }>;
}

export interface MatchingOutput {
  evidenceId: string;
  paymentId: string | null;
  confidence: number;
  confidenceLevel: ConfidenceLevel;
  scoreBreakdown: ScoreBreakdown;
  decision: MatchingDecision;
}

export class MatchingService {
  constructor(
    private matchingRepo: MatchingRepository,
    private evidenceRepo: EvidenceRepository,
    private paymentRepo: PaymentRepository,
    private deviceRepo: DeviceRepository,
    private reviewService: ReviewService,
    private paymentService: PaymentService,
  ) {}

  async match(input: MatchingInput): Promise<MatchingOutput> {
    const { evidence, pendingPayments } = input;

    const matchingPayments = pendingPayments.filter((p) => p.amount === evidence.amount);

    if (matchingPayments.length === 0) {
      const result: MatchingOutput = {
        evidenceId: evidence.id,
        paymentId: null,
        confidence: 0,
        confidenceLevel: ConfidenceLevel.UNKNOWN,
        scoreBreakdown: { time_proximity: 0, utr_uniqueness: 0, single_pending_match: 0, device_trust: 0, evidence_quality: 0, total: 0 },
        decision: MatchingDecision.DISCARDED,
      };
      await this.storeResult(evidence.id, result);
      return result;
    }

    const device = await this.deviceRepo.findById(evidence.device_id);
    const utr = evidence.utr;

    let bestScore = 0;
    let bestPaymentId: string | null = null;
    let bestBreakdown: ScoreBreakdown = { time_proximity: 0, utr_uniqueness: 0, single_pending_match: 0, device_trust: 0, evidence_quality: 0, total: 0 };

    for (const payment of matchingPayments) {
      const breakdown = this.calculateScore(evidence, payment, matchingPayments.length, device?.health_score ?? 0, utr);
      if (breakdown.total > bestScore) {
        bestScore = breakdown.total;
        bestPaymentId = payment.id;
        bestBreakdown = breakdown;
      }
    }

    // MVP: one pending payment with exact amount within 60 min → auto-confirm
    if (matchingPayments.length === 1 && bestPaymentId) {
      const minutesDelta = Math.abs(
        evidence.notification_timestamp.getTime() - matchingPayments[0]!.created_at.getTime(),
      ) / 60000;
      if (minutesDelta <= 60) {
        bestScore = Math.max(bestScore, CONFIDENCE_THRESHOLDS.HIGH);
      }
    }

    const confidenceLevel = getConfidenceLevel(bestScore);
    let decision: MatchingDecision;

    if (bestScore >= CONFIDENCE_THRESHOLDS.HIGH) {
      decision = MatchingDecision.AUTO_CONFIRMED;
    } else if (bestScore >= CONFIDENCE_THRESHOLDS.MEDIUM) {
      decision = MatchingDecision.FLAGGED;
    } else if (bestScore >= CONFIDENCE_THRESHOLDS.LOW) {
      decision = MatchingDecision.REVIEW;
    } else {
      decision = MatchingDecision.DISCARDED;
    }

    const result: MatchingOutput = {
      evidenceId: evidence.id,
      paymentId: bestPaymentId,
      confidence: bestScore,
      confidenceLevel,
      scoreBreakdown: bestBreakdown,
      decision,
    };

    const matchingResultId = await this.storeResult(evidence.id, result);

    if (result.paymentId && (decision === MatchingDecision.AUTO_CONFIRMED || decision === MatchingDecision.FLAGGED)) {
      await this.evidenceRepo.markMatched(evidence.id, result.paymentId);
      await this.paymentService.markSuccess(result.paymentId, {
        evidence_id: evidence.id,
        confidence: bestScore,
        confidence_level: confidenceLevel,
        matched_at: new Date().toISOString(),
        score_breakdown: bestBreakdown,
      });
    } else if (result.paymentId && decision === MatchingDecision.REVIEW) {
      await this.evidenceRepo.markMatched(evidence.id, result.paymentId);
      await this.paymentRepo.updateStatus(result.paymentId, PaymentStatus.REVIEW, {
        evidence_id: evidence.id,
        confidence: bestScore,
        matched_at: new Date(),
      } as any);
      await this.reviewService.createReview(result.paymentId, evidence.id, matchingResultId);
    }

    return result;
  }

  private calculateScore(
    evidence: PaymentEvidence,
    payment: { id: string; amount: number; created_at: Date },
    pendingCount: number,
    deviceHealthScore: number,
    utr: string | null,
  ): ScoreBreakdown {
    let timeProximity = 0;
    const timeDelta = evidence.notification_timestamp.getTime() - payment.created_at.getTime();
    const minutesDelta = Math.abs(timeDelta) / 60000;

    if (minutesDelta < 5) timeProximity = MATCHING_SCORES.TIME_WITHIN_5MIN;
    else if (minutesDelta < 15) timeProximity = MATCHING_SCORES.TIME_WITHIN_15MIN;
    else if (minutesDelta < 30) timeProximity = MATCHING_SCORES.TIME_WITHIN_30MIN;

    let utrUniqueness = 0;
    if (utr) {
      utrUniqueness = MATCHING_SCORES.UTR_UNIQUE;
    }

    const singlePendingMatch = pendingCount === 1 ? MATCHING_SCORES.SINGLE_PENDING_MATCH : 0;

    let deviceTrust = 0;
    if (deviceHealthScore >= 70) deviceTrust = MATCHING_SCORES.DEVICE_HEALTH_GOOD;
    else if (deviceHealthScore >= 40) deviceTrust = MATCHING_SCORES.DEVICE_HEALTH_WARNING;

    let evidenceQuality = 0;
    if (utr && evidence.sender_name) evidenceQuality = MATCHING_SCORES.EVIDENCE_FULL;
    else if (utr || evidence.sender_name) evidenceQuality = MATCHING_SCORES.EVIDENCE_PARTIAL;

    const total = timeProximity + utrUniqueness + singlePendingMatch + deviceTrust + evidenceQuality;

    return {
      time_proximity: timeProximity,
      utr_uniqueness: utrUniqueness,
      single_pending_match: singlePendingMatch,
      device_trust: deviceTrust,
      evidence_quality: evidenceQuality,
      total,
    };
  }

  private async storeResult(evidenceId: string, result: MatchingOutput): Promise<string> {
    const id = generateId();
    await this.matchingRepo.create({
      id,
      evidence_id: evidenceId,
      payment_id: result.paymentId,
      confidence: result.confidence,
      confidence_level: result.confidenceLevel,
      score_breakdown: result.scoreBreakdown as unknown as Record<string, number>,
      decision: result.decision,
    });
    return id;
  }

  async getMatchingForEvidence(evidenceId: string) {
    return this.matchingRepo.findByEvidenceId(evidenceId);
  }

  async getMatchingForPayment(paymentId: string) {
    return this.matchingRepo.findByPaymentId(paymentId);
  }

  /** Run matching immediately after evidence upload (MVP — do not rely only on BullMQ). */
  async runMatchingForEvidence(evidenceId: string): Promise<MatchingOutput> {
    const evidence = await this.evidenceRepo.findById(evidenceId);
    if (!evidence) {
      throw new Error(`Evidence ${evidenceId} not found`);
    }

    const pendingPayments = await this.paymentRepo.findByMerchantAndAmount(
      evidence.merchant_id,
      evidence.amount,
    );

    const matchingPayments = pendingPayments.filter(
      (p) => p.status === PaymentStatus.PENDING,
    );

    return this.match({
      evidence,
      pendingPayments: matchingPayments.map((p) => ({
        id: p.id,
        amount: p.amount,
        created_at: p.created_at,
        merchant_id: p.merchant_id,
      })),
    });
  }

  async retryMatch(paymentId: string): Promise<MatchingOutput | null> {
    const payment = await this.paymentRepo.findById(paymentId);
    if (!payment) return null;

    const evidenceList = await this.evidenceRepo.findByPaymentId(paymentId);
    if (evidenceList.length === 0) return null;

    const latestEvidence = evidenceList[0];
    const pendingPayments = await this.paymentRepo.findByMerchantAndAmount(
      payment.merchant_id,
      payment.amount,
    );

    return this.match({
      evidence: latestEvidence,
      pendingPayments: pendingPayments.map((p) => ({
        id: p.id,
        amount: p.amount,
        created_at: p.created_at,
        merchant_id: p.merchant_id,
      })),
    });
  }
}
