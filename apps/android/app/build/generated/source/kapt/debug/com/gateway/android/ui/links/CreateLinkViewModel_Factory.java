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
public final class CreateLinkViewModel_Factory implements Factory<CreateLinkViewModel> {
  private final Provider<MerchantRepository> merchantRepoProvider;

  public CreateLinkViewModel_Factory(Provider<MerchantRepository> merchantRepoProvider) {
    this.merchantRepoProvider = merchantRepoProvider;
  }

  @Override
  public CreateLinkViewModel get() {
    return newInstance(merchantRepoProvider.get());
  }

  public static CreateLinkViewModel_Factory create(
      Provider<MerchantRepository> merchantRepoProvider) {
    return new CreateLinkViewModel_Factory(merchantRepoProvider);
  }

  public static CreateLinkViewModel newInstance(MerchantRepository merchantRepo) {
    return new CreateLinkViewModel(merchantRepo);
  }
}
