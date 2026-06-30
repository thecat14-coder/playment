package com.gateway.android.service;

import com.gateway.android.data.repo.AuthRepository;
import com.gateway.android.data.repo.DeviceRepository;
import com.gateway.android.data.repo.EvidenceRepository;
import com.gateway.android.data.repo.OnlineStateRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ForegroundService_MembersInjector implements MembersInjector<ForegroundService> {
  private final Provider<DeviceRepository> deviceRepoProvider;

  private final Provider<AuthRepository> authRepoProvider;

  private final Provider<EvidenceRepository> evidenceRepoProvider;

  private final Provider<OnlineStateRepository> onlineStateRepoProvider;

  public ForegroundService_MembersInjector(Provider<DeviceRepository> deviceRepoProvider,
      Provider<AuthRepository> authRepoProvider, Provider<EvidenceRepository> evidenceRepoProvider,
      Provider<OnlineStateRepository> onlineStateRepoProvider) {
    this.deviceRepoProvider = deviceRepoProvider;
    this.authRepoProvider = authRepoProvider;
    this.evidenceRepoProvider = evidenceRepoProvider;
    this.onlineStateRepoProvider = onlineStateRepoProvider;
  }

  public static MembersInjector<ForegroundService> create(
      Provider<DeviceRepository> deviceRepoProvider, Provider<AuthRepository> authRepoProvider,
      Provider<EvidenceRepository> evidenceRepoProvider,
      Provider<OnlineStateRepository> onlineStateRepoProvider) {
    return new ForegroundService_MembersInjector(deviceRepoProvider, authRepoProvider, evidenceRepoProvider, onlineStateRepoProvider);
  }

  @Override
  public void injectMembers(ForegroundService instance) {
    injectDeviceRepo(instance, deviceRepoProvider.get());
    injectAuthRepo(instance, authRepoProvider.get());
    injectEvidenceRepo(instance, evidenceRepoProvider.get());
    injectOnlineStateRepo(instance, onlineStateRepoProvider.get());
  }

  @InjectedFieldSignature("com.gateway.android.service.ForegroundService.deviceRepo")
  public static void injectDeviceRepo(ForegroundService instance, DeviceRepository deviceRepo) {
    instance.deviceRepo = deviceRepo;
  }

  @InjectedFieldSignature("com.gateway.android.service.ForegroundService.authRepo")
  public static void injectAuthRepo(ForegroundService instance, AuthRepository authRepo) {
    instance.authRepo = authRepo;
  }

  @InjectedFieldSignature("com.gateway.android.service.ForegroundService.evidenceRepo")
  public static void injectEvidenceRepo(ForegroundService instance,
      EvidenceRepository evidenceRepo) {
    instance.evidenceRepo = evidenceRepo;
  }

  @InjectedFieldSignature("com.gateway.android.service.ForegroundService.onlineStateRepo")
  public static void injectOnlineStateRepo(ForegroundService instance,
      OnlineStateRepository onlineStateRepo) {
    instance.onlineStateRepo = onlineStateRepo;
  }
}
