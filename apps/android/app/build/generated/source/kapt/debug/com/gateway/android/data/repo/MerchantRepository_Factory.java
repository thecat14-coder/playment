package com.gateway.android.data.repo;

import com.gateway.android.data.api.GatewayApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class MerchantRepository_Factory implements Factory<MerchantRepository> {
  private final Provider<GatewayApi> apiProvider;

  private final Provider<AuthRepository> authRepoProvider;

  public MerchantRepository_Factory(Provider<GatewayApi> apiProvider,
      Provider<AuthRepository> authRepoProvider) {
    this.apiProvider = apiProvider;
    this.authRepoProvider = authRepoProvider;
  }

  @Override
  public MerchantRepository get() {
    return newInstance(apiProvider.get(), authRepoProvider.get());
  }

  public static MerchantRepository_Factory create(Provider<GatewayApi> apiProvider,
      Provider<AuthRepository> authRepoProvider) {
    return new MerchantRepository_Factory(apiProvider, authRepoProvider);
  }

  public static MerchantRepository newInstance(GatewayApi api, AuthRepository authRepo) {
    return new MerchantRepository(api, authRepo);
  }
}
