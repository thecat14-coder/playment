package com.gateway.android.ui.health;

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
public final class DeviceHealthViewModel_Factory implements Factory<DeviceHealthViewModel> {
  private final Provider<DeviceRepository> deviceRepoProvider;

  private final Provider<MerchantRepository> merchantRepoProvider;

  private final Provider<OnlineStateRepository> onlineStateRepoProvider;

  public DeviceHealthViewModel_Factory(Provider<DeviceRepository> deviceRepoProvider,
      Provider<MerchantRepository> merchantRepoProvider,
      Provider<OnlineStateRepository> onlineStateRepoProvider) {
    this.deviceRepoProvider = deviceRepoProvider;
    this.merchantRepoProvider = merchantRepoProvider;
    this.onlineStateRepoProvider = onlineStateRepoProvider;
  }

  @Override
  public DeviceHealthViewModel get() {
    return newInstance(deviceRepoProvider.get(), merchantRepoProvider.get(), onlineStateRepoProvider.get());
  }

  public static DeviceHealthViewModel_Factory create(Provider<DeviceRepository> deviceRepoProvider,
      Provider<MerchantRepository> merchantRepoProvider,
      Provider<OnlineStateRepository> onlineStateRepoProvider) {
    return new DeviceHealthViewModel_Factory(deviceRepoProvider, merchantRepoProvider, onlineStateRepoProvider);
  }

  public static DeviceHealthViewModel newInstance(DeviceRepository deviceRepo,
      MerchantRepository merchantRepo, OnlineStateRepository onlineStateRepo) {
    return new DeviceHealthViewModel(deviceRepo, merchantRepo, onlineStateRepo);
  }
}
