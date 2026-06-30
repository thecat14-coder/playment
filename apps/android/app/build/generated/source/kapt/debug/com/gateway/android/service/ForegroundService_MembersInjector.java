package com.gateway.android.service;

import com.gateway.android.data.repo.AuthRepository;
import com.gateway.android.data.repo.DeviceRepository;
import com.gateway.android.data.repo.EvidenceRepository;
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

  public ForegroundService_MembersInjector(Provider<DeviceRepository> deviceRepoProvider,
      Provider<AuthRepository> authRepoProvider,
      Provider<EvidenceRepository> evidenceRepoProvider) {
    this.deviceRepoProvider = deviceRepoProvider;
    this.authRepoProvider = authRepoProvider;
    this.evidenceRepoProvider = evidenceRepoProvider;
  }

  public static MembersInjector<ForegroundService> create(
      Provider<DeviceRepository> deviceRepoProvider, Provider<AuthRepository> authRepoProvider,
      Provider<EvidenceRepository> evidenceRepoProvider) {
    return new ForegroundService_MembersInjector(deviceRepoProvider, authRepoProvider, evidenceRepoProvider);
  }

  @Override
  public void injectMembers(ForegroundService instance) {
    injectDeviceRepo(instance, deviceRepoProvider.get());
    injectAuthRepo(instance, authRepoProvider.get());
    injectEvidenceRepo(instance, evidenceRepoProvider.get());
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
}
