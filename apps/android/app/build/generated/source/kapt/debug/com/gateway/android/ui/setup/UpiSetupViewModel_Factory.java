package com.gateway.android.ui.setup;

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
public final class UpiSetupViewModel_Factory implements Factory<UpiSetupViewModel> {
  private final Provider<MerchantRepository> merchantRepoProvider;

  public UpiSetupViewModel_Factory(Provider<MerchantRepository> merchantRepoProvider) {
    this.merchantRepoProvider = merchantRepoProvider;
  }

  @Override
  public UpiSetupViewModel get() {
    return newInstance(merchantRepoProvider.get());
  }

  public static UpiSetupViewModel_Factory create(
      Provider<MerchantRepository> merchantRepoProvider) {
    return new UpiSetupViewModel_Factory(merchantRepoProvider);
  }

  public static UpiSetupViewModel newInstance(MerchantRepository merchantRepo) {
    return new UpiSetupViewModel(merchantRepo);
  }
}
