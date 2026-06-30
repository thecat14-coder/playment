package com.gateway.android.service;

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
public final class NotificationListenerService_MembersInjector implements MembersInjector<NotificationListenerService> {
  private final Provider<EvidenceRepository> evidenceRepoProvider;

  public NotificationListenerService_MembersInjector(
      Provider<EvidenceRepository> evidenceRepoProvider) {
    this.evidenceRepoProvider = evidenceRepoProvider;
  }

  public static MembersInjector<NotificationListenerService> create(
      Provider<EvidenceRepository> evidenceRepoProvider) {
    return new NotificationListenerService_MembersInjector(evidenceRepoProvider);
  }

  @Override
  public void injectMembers(NotificationListenerService instance) {
    injectEvidenceRepo(instance, evidenceRepoProvider.get());
  }

  @InjectedFieldSignature("com.gateway.android.service.NotificationListenerService.evidenceRepo")
  public static void injectEvidenceRepo(NotificationListenerService instance,
      EvidenceRepository evidenceRepo) {
    instance.evidenceRepo = evidenceRepo;
  }
}
