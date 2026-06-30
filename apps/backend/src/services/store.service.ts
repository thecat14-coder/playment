import type { CreateStoreInput, UpdateStoreInput, CreateCounterInput } from '@gateway/shared';
import type { StoreRepository } from '../repositories/store.repository.js';
import { generateId } from '../utils/ulid.js';
import { NotFoundError, ConflictError } from '../utils/errors.js';

export class StoreService {
  constructor(private storeRepo: StoreRepository) {}

  async createStore(merchantId: string, input: CreateStoreInput) {
    const store = await this.storeRepo.createStore({
      id: generateId(),
      merchant_id: merchantId,
      name: input.name,
      address: input.address ?? null,
      upi_id: input.upi_id ?? null,
    });

    return store;
  }

  async listStores(merchantId: string) {
    return this.storeRepo.findStoresByMerchant(merchantId);
  }

  async updateStore(storeId: string, merchantId: string, input: UpdateStoreInput) {
    const store = await this.storeRepo.findStoreById(storeId);
    if (!store) throw new NotFoundError('Store not found');
    if (store.merchant_id !== merchantId) throw new NotFoundError('Store not found');

    const updated = await this.storeRepo.updateStore(storeId, input as any);
    return updated;
  }

  async deleteStore(storeId: string, merchantId: string) {
    const store = await this.storeRepo.findStoreById(storeId);
    if (!store) throw new NotFoundError('Store not found');
    if (store.merchant_id !== merchantId) throw new NotFoundError('Store not found');

    await this.storeRepo.deleteStore(storeId);
  }

  async createCounter(storeId: string, merchantId: string, input: CreateCounterInput) {
    const store = await this.storeRepo.findStoreById(storeId);
    if (!store) throw new NotFoundError('Store not found');
    if (store.merchant_id !== merchantId) throw new NotFoundError('Store not found');

    const counter = await this.storeRepo.createCounter({
      id: generateId(),
      store_id: storeId,
      label: input.label,
    });

    return counter;
  }

  async listCounters(storeId: string, merchantId: string) {
    const store = await this.storeRepo.findStoreById(storeId);
    if (!store) throw new NotFoundError('Store not found');
    if (store.merchant_id !== merchantId) throw new NotFoundError('Store not found');

    return this.storeRepo.findCountersByStore(storeId);
  }
}
