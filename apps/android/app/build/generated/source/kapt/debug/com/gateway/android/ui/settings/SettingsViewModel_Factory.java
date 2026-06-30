package com.gateway.android.ui.settings;

import android.content.Context;
import com.gateway.android.data.repo.AuthRepository;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<AuthRepository> authRepoProvider;

  private final Provider<MerchantRepository> merchantRepoProvider;

  private final Provider<OnlineStateRepository> onlineStateRepoProvider;

  private final Provider<Context> contextProvider;

  public SettingsViewModel_Factory(Provider<AuthRepository> authRepoProvider,
      Provider<MerchantRepository> merchantRepoProvider,
      Provider<OnlineStateRepository> onlineStateRepoProvider, Provider<Context> contextProvider) {
    this.authRepoProvider = authRepoProvider;
    this.merchantRepoProvider = merchantRepoProvider;
    this.onlineStateRepoProvider = onlineStateRepoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(authRepoProvider.get(), merchantRepoProvider.get(), onlineStateRepoProvider.get(), contextProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<AuthRepository> authRepoProvider,
      Provider<MerchantRepository> merchantRepoProvider,
      Provider<OnlineStateRepository> onlineStateRepoProvider, Provider<Context> contextProvider) {
    return new SettingsViewModel_Factory(authRepoProvider, merchantRepoProvider, onlineStateRepoProvider, contextProvider);
  }

  public static SettingsViewModel newInstance(AuthRepository authRepo,
      MerchantRepository merchantRepo, OnlineStateRepository onlineStateRepo, Context context) {
    return new SettingsViewModel(authRepo, merchantRepo, onlineStateRepo, context);
  }
}
