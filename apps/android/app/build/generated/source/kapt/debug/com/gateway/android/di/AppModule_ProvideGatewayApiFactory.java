package com.gateway.android.di;

import com.gateway.android.data.api.GatewayApi;
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
public final class AppModule_ProvideGatewayApiFactory implements Factory<GatewayApi> {
  private final Provider<OkHttpClient> clientProvider;

  public AppModule_ProvideGatewayApiFactory(Provider<OkHttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public GatewayApi get() {
    return provideGatewayApi(clientProvider.get());
  }

  public static AppModule_ProvideGatewayApiFactory create(Provider<OkHttpClient> clientProvider) {
    return new AppModule_ProvideGatewayApiFactory(clientProvider);
  }

  public static GatewayApi provideGatewayApi(OkHttpClient client) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideGatewayApi(client));
  }
}
