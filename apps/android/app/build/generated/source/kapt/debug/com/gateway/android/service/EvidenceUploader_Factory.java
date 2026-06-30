package com.gateway.android.service;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.gateway.android.data.repo.EvidenceRepository;
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
public final class EvidenceUploader_Factory {
  private final Provider<EvidenceRepository> evidenceRepoProvider;

  public EvidenceUploader_Factory(Provider<EvidenceRepository> evidenceRepoProvider) {
    this.evidenceRepoProvider = evidenceRepoProvider;
  }

  public EvidenceUploader get(Context context, WorkerParameters params) {
    return newInstance(context, params, evidenceRepoProvider.get());
  }

  public static EvidenceUploader_Factory create(Provider<EvidenceRepository> evidenceRepoProvider) {
    return new EvidenceUploader_Factory(evidenceRepoProvider);
  }

  public static EvidenceUploader newInstance(Context context, WorkerParameters params,
      EvidenceRepository evidenceRepo) {
    return new EvidenceUploader(context, params, evidenceRepo);
  }
}
