package com.gateway.android.domain.usecase;

import com.gateway.android.data.repo.EvidenceRepository;
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
public final class ProcessNotificationUseCase_Factory implements Factory<ProcessNotificationUseCase> {
  private final Provider<EvidenceRepository> evidenceRepoProvider;

  public ProcessNotificationUseCase_Factory(Provider<EvidenceRepository> evidenceRepoProvider) {
    this.evidenceRepoProvider = evidenceRepoProvider;
  }

  @Override
  public ProcessNotificationUseCase get() {
    return newInstance(evidenceRepoProvider.get());
  }

  public static ProcessNotificationUseCase_Factory create(
      Provider<EvidenceRepository> evidenceRepoProvider) {
    return new ProcessNotificationUseCase_Factory(evidenceRepoProvider);
  }

  public static ProcessNotificationUseCase newInstance(EvidenceRepository evidenceRepo) {
    return new ProcessNotificationUseCase(evidenceRepo);
  }
}
