package com.gateway.android.service;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.gateway.android.data.repo.AuthRepository;
import com.gateway.android.data.repo.DeviceRepository;
import dagger.internal.DaggerGenerated;
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
public final class HealthReporter_Factory {
  private final Provider<DeviceRepository> deviceRepoProvider;

  private final Provider<AuthRepository> authRepoProvider;

  public HealthReporter_Factory(Provider<DeviceRepository> deviceRepoProvider,
      Provider<AuthRepository> authRepoProvider) {
    this.deviceRepoProvider = deviceRepoProvider;
    this.authRepoProvider = authRepoProvider;
  }

  public HealthReporter get(Context context, WorkerParameters params) {
    return newInstance(context, params, deviceRepoProvider.get(), authRepoProvider.get());
  }

  public static HealthReporter_Factory create(Provider<DeviceRepository> deviceRepoProvider,
      Provider<AuthRepository> authRepoProvider) {
    return new HealthReporter_Factory(deviceRepoProvider, authRepoProvider);
  }

  public static HealthReporter newInstance(Context context, WorkerParameters params,
      DeviceRepository deviceRepo, AuthRepository authRepo) {
    return new HealthReporter(context, params, deviceRepo, authRepo);
  }
}
