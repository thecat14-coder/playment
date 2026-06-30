package com.gateway.android.service;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class EvidenceUploader_AssistedFactory_Impl implements EvidenceUploader_AssistedFactory {
  private final EvidenceUploader_Factory delegateFactory;

  EvidenceUploader_AssistedFactory_Impl(EvidenceUploader_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public EvidenceUploader create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<EvidenceUploader_AssistedFactory> create(
      EvidenceUploader_Factory delegateFactory) {
    return InstanceFactory.create(new EvidenceUploader_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<EvidenceUploader_AssistedFactory> createFactoryProvider(
      EvidenceUploader_Factory delegateFactory) {
    return InstanceFactory.create(new EvidenceUploader_AssistedFactory_Impl(delegateFactory));
  }
}
