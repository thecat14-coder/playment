package com.gateway.android.data.repo;

import android.content.Context;
import com.gateway.android.data.api.GatewayApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DeviceRepository_Factory implements Factory<DeviceRepository> {
  private final Provider<GatewayApi> apiProvider;

  private final Provider<AuthRepository> authRepoProvider;

  private final Provider<Context> contextProvider;

  public DeviceRepository_Factory(Provider<GatewayApi> apiProvider,
      Provider<AuthRepository> authRepoProvider, Provider<Context> contextProvider) {
    this.apiProvider = apiProvider;
    this.authRepoProvider = authRepoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public DeviceRepository get() {
    return newInstance(apiProvider.get(), authRepoProvider.get(), contextProvider.get());
  }

  public static DeviceRepository_Factory create(Provider<GatewayApi> apiProvider,
      Provider<AuthRepository> authRepoProvider, Provider<Context> contextProvider) {
    return new DeviceRepository_Factory(apiProvider, authRepoProvider, contextProvider);
  }

  public static DeviceRepository newInstance(GatewayApi api, AuthRepository authRepo,
      Context context) {
    return new DeviceRepository(api, authRepo, context);
  }
}
