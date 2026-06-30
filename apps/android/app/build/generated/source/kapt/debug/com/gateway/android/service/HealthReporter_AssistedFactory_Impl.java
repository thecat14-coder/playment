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
public final class HealthReporter_AssistedFactory_Impl implements HealthReporter_AssistedFactory {
  private final HealthReporter_Factory delegateFactory;

  HealthReporter_AssistedFactory_Impl(HealthReporter_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public HealthReporter create(Context arg0, WorkerParameters arg1) {
    return delegateFactory.get(arg0, arg1);
  }

  public static Provider<HealthReporter_AssistedFactory> create(
      HealthReporter_Factory delegateFactory) {
    return InstanceFactory.create(new HealthReporter_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<HealthReporter_AssistedFactory> createFactoryProvider(
      HealthReporter_Factory delegateFactory) {
    return InstanceFactory.create(new HealthReporter_AssistedFactory_Impl(delegateFactory));
  }
}
