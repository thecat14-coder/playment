package com.gateway.android.ui.login;

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
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<AuthRepository> authRepoProvider;

  private final Provider<DeviceRepository> deviceRepoProvider;

  public AuthViewModel_Factory(Provider<AuthRepository> authRepoProvider,
      Provider<DeviceRepository> deviceRepoProvider) {
    this.authRepoProvider = authRepoProvider;
    this.deviceRepoProvider = deviceRepoProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(authRepoProvider.get(), deviceRepoProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<AuthRepository> authRepoProvider,
      Provider<DeviceRepository> deviceRepoProvider) {
    return new AuthViewModel_Factory(authRepoProvider, deviceRepoProvider);
  }

  public static AuthViewModel newInstance(AuthRepository authRepo, DeviceRepository deviceRepo) {
    return new AuthViewModel(authRepo, deviceRepo);
  }
}
