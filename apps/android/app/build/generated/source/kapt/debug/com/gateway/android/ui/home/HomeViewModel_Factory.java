package com.gateway.android.ui.home;

import android.content.Context;
import com.gateway.android.data.repo.AuthRepository;
import com.gateway.android.data.repo.DeviceRepository;
import com.gateway.android.data.repo.MerchantRepository;
import com.gateway.android.data.repo.OnlineStateRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<AuthRepository> authRepoProvider;

  private final Provider<MerchantRepository> merchantRepoProvider;

  private final Provider<DeviceRepository> deviceRepoProvider;

  private final Provider<OnlineStateRepository> onlineStateRepoProvider;

  private final Provider<Context> contextProvider;

  public HomeViewModel_Factory(Provider<AuthRepository> authRepoProvider,
      Provider<MerchantRepository> merchantRepoProvider,
      Provider<DeviceRepository> deviceRepoProvider,
      Provider<OnlineStateRepository> onlineStateRepoProvider, Provider<Context> contextProvider) {
    this.authRepoProvider = authRepoProvider;
    this.merchantRepoProvider = merchantRepoProvider;
    this.deviceRepoProvider = deviceRepoProvider;
    this.onlineStateRepoProvider = onlineStateRepoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(authRepoProvider.get(), merchantRepoProvider.get(), deviceRepoProvider.get(), onlineStateRepoProvider.get(), contextProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<AuthRepository> authRepoProvider,
      Provider<MerchantRepository> merchantRepoProvider,
      Provider<DeviceRepository> deviceRepoProvider,
      Provider<OnlineStateRepository> onlineStateRepoProvider, Provider<Context> contextProvider) {
    return new HomeViewModel_Factory(authRepoProvider, merchantRepoProvider, deviceRepoProvider, onlineStateRepoProvider, contextProvider);
  }

  public static HomeViewModel newInstance(AuthRepository authRepo, MerchantRepository merchantRepo,
      DeviceRepository deviceRepo, OnlineStateRepository onlineStateRepo, Context context) {
    return new HomeViewModel(authRepo, merchantRepo, deviceRepo, onlineStateRepo, context);
  }
}
