package com.gateway.android.ui.payments;

import com.gateway.android.data.repo.MerchantRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class PaymentsViewModel_Factory implements Factory<PaymentsViewModel> {
  private final Provider<MerchantRepository> merchantRepoProvider;

  public PaymentsViewModel_Factory(Provider<MerchantRepository> merchantRepoProvider) {
    this.merchantRepoProvider = merchantRepoProvider;
  }

  @Override
  public PaymentsViewModel get() {
    return newInstance(merchantRepoProvider.get());
  }

  public static PaymentsViewModel_Factory create(
      Provider<MerchantRepository> merchantRepoProvider) {
    return new PaymentsViewModel_Factory(merchantRepoProvider);
  }

  public static PaymentsViewModel newInstance(MerchantRepository merchantRepo) {
    return new PaymentsViewModel(merchantRepo);
  }
}
