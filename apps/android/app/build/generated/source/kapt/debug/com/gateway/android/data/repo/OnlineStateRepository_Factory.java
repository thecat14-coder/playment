package com.gateway.android.data.repo;

import android.content.SharedPreferences;
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
public final class OnlineStateRepository_Factory implements Factory<OnlineStateRepository> {
  private final Provider<GatewayApi> apiProvider;

  private final Provider<AuthRepository> authRepoProvider;

  private final Provider<SharedPreferences> preferencesProvider;

  public OnlineStateRepository_Factory(Provider<GatewayApi> apiProvider,
      Provider<AuthRepository> authRepoProvider, Provider<SharedPreferences> preferencesProvider) {
    this.apiProvider = apiProvider;
    this.authRepoProvider = authRepoProvider;
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public OnlineStateRepository get() {
    return newInstance(apiProvider.get(), authRepoProvider.get(), preferencesProvider.get());
  }

  public static OnlineStateRepository_Factory create(Provider<GatewayApi> apiProvider,
      Provider<AuthRepository> authRepoProvider, Provider<SharedPreferences> preferencesProvider) {
    return new OnlineStateRepository_Factory(apiProvider, authRepoProvider, preferencesProvider);
  }

  public static OnlineStateRepository newInstance(GatewayApi api, AuthRepository authRepo,
      SharedPreferences preferences) {
    return new OnlineStateRepository(api, authRepo, preferences);
  }
}
