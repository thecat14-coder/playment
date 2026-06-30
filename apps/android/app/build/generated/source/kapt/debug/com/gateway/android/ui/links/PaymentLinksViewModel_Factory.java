package com.gateway.android.ui.links;

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
public final class PaymentLinksViewModel_Factory implements Factory<PaymentLinksViewModel> {
  private final Provider<MerchantRepository> merchantRepoProvider;

  public PaymentLinksViewModel_Factory(Provider<MerchantRepository> merchantRepoProvider) {
    this.merchantRepoProvider = merchantRepoProvider;
  }

  @Override
  public PaymentLinksViewModel get() {
    return newInstance(merchantRepoProvider.get());
  }

  public static PaymentLinksViewModel_Factory create(
      Provider<MerchantRepository> merchantRepoProvider) {
    return new PaymentLinksViewModel_Factory(merchantRepoProvider);
  }

  public static PaymentLinksViewModel newInstance(MerchantRepository merchantRepo) {
    return new PaymentLinksViewModel(merchantRepo);
  }
}
