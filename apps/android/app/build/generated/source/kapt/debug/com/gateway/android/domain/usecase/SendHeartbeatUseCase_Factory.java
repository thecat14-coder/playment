package com.gateway.android.domain.usecase;

import com.gateway.android.data.repo.AuthRepository;
import com.gateway.android.data.repo.DeviceRepository;
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
public final class SendHeartbeatUseCase_Factory implements Factory<SendHeartbeatUseCase> {
  private final Provider<DeviceRepository> deviceRepoProvider;

  private final Provider<AuthRepository> authRepoProvider;

  public SendHeartbeatUseCase_Factory(Provider<DeviceRepository> deviceRepoProvider,
      Provider<AuthRepository> authRepoProvider) {
    this.deviceRepoProvider = deviceRepoProvider;
    this.authRepoProvider = authRepoProvider;
  }

  @Override
  public SendHeartbeatUseCase get() {
    return newInstance(deviceRepoProvider.get(), authRepoProvider.get());
  }

  public static SendHeartbeatUseCase_Factory create(Provider<DeviceRepository> deviceRepoProvider,
      Provider<AuthRepository> authRepoProvider) {
    return new SendHeartbeatUseCase_Factory(deviceRepoProvider, authRepoProvider);
  }

  public static SendHeartbeatUseCase newInstance(DeviceRepository deviceRepo,
      AuthRepository authRepo) {
    return new SendHeartbeatUseCase(deviceRepo, authRepo);
  }
}
