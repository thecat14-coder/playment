package com.gateway.android.di;

import android.content.SharedPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

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
public final class AppModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<SharedPreferences> preferencesProvider;

  public AppModule_ProvideOkHttpClientFactory(Provider<SharedPreferences> preferencesProvider) {
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(preferencesProvider.get());
  }

  public static AppModule_ProvideOkHttpClientFactory create(
      Provider<SharedPreferences> preferencesProvider) {
    return new AppModule_ProvideOkHttpClientFactory(preferencesProvider);
  }

  public static OkHttpClient provideOkHttpClient(SharedPreferences preferences) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideOkHttpClient(preferences));
  }
}
