import { describe, it, expect } from 'vitest';
import {
  AppError,
  ValidationError,
  UnauthorizedError,
  ForbiddenError,
  NotFoundError,
  RateLimitError,
  ConflictError,
} from '../../src/utils/errors.js';

describe('Error classes', () => {
  it('AppError serializes to standard format', () => {
    const err = new AppError(400, 'TEST_ERROR', 'Something broke', [
      { field: 'name', issue: 'required' },
    ]);

    expect(err.statusCode).toBe(400);
    expect(err.code).toBe('TEST_ERROR');
    expect(err.toJSON()).toEqual({
      error: {
        code: 'TEST_ERROR',
        message: 'Something broke',
        details: [{ field: 'name', issue: 'required' }],
      },
    });
  });

  it('AppError without details omits details field', () => {
    const err = new AppError(500, 'INTERNAL', 'Oops');
    const json = err.toJSON();
    expect(json.error).not.toHaveProperty('details');
  });

  it('ValidationError is 400', () => {
    const err = new ValidationError('bad input');
    expect(err.statusCode).toBe(400);
    expect(err.code).toBe('VALIDATION_ERROR');
  });

  it('UnauthorizedError is 401', () => {
    expect(new UnauthorizedError().statusCode).toBe(401);
    expect(new UnauthorizedError().code).toBe('UNAUTHORIZED');
  });

  it('ForbiddenError is 403', () => {
    expect(new ForbiddenError().statusCode).toBe(403);
  });

  it('NotFoundError is 404', () => {
    expect(new NotFoundError().statusCode).toBe(404);
  });

  it('RateLimitError is 429', () => {
    expect(new RateLimitError().statusCode).toBe(429);
  });

  it('ConflictError is 409', () => {
    expect(new ConflictError('duplicate').statusCode).toBe(409);
  });

  it('all errors extend Error', () => {
    expect(new ValidationError('x')).toBeInstanceOf(Error);
    expect(new NotFoundError()).toBeInstanceOf(AppError);
  });
});
