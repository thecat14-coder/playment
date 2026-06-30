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
public final class AuthRepository_Factory implements Factory<AuthRepository> {
  private final Provider<GatewayApi> apiProvider;

  private final Provider<SharedPreferences> preferencesProvider;

  public AuthRepository_Factory(Provider<GatewayApi> apiProvider,
      Provider<SharedPreferences> preferencesProvider) {
    this.apiProvider = apiProvider;
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public AuthRepository get() {
    return newInstance(apiProvider.get(), preferencesProvider.get());
  }

  public static AuthRepository_Factory create(Provider<GatewayApi> apiProvider,
      Provider<SharedPreferences> preferencesProvider) {
    return new AuthRepository_Factory(apiProvider, preferencesProvider);
  }

  public static AuthRepository newInstance(GatewayApi api, SharedPreferences preferences) {
    return new AuthRepository(api, preferences);
  }
}
