package com.gateway.android.ui.developer;

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
public final class DeveloperViewModel_Factory implements Factory<DeveloperViewModel> {
  private final Provider<MerchantRepository> merchantRepoProvider;

  public DeveloperViewModel_Factory(Provider<MerchantRepository> merchantRepoProvider) {
    this.merchantRepoProvider = merchantRepoProvider;
  }

  @Override
  public DeveloperViewModel get() {
    return newInstance(merchantRepoProvider.get());
  }

  public static DeveloperViewModel_Factory create(
      Provider<MerchantRepository> merchantRepoProvider) {
    return new DeveloperViewModel_Factory(merchantRepoProvider);
  }

  public static DeveloperViewModel newInstance(MerchantRepository merchantRepo) {
    return new DeveloperViewModel(merchantRepo);
  }
}
