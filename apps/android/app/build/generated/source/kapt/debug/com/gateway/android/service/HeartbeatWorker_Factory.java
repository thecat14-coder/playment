package com.gateway.android.service;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.gateway.android.domain.usecase.SendHeartbeatUseCase;
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
public final class HeartbeatWorker_Factory {
  private final Provider<SendHeartbeatUseCase> sendHeartbeatProvider;

  public HeartbeatWorker_Factory(Provider<SendHeartbeatUseCase> sendHeartbeatProvider) {
    this.sendHeartbeatProvider = sendHeartbeatProvider;
  }

  public HeartbeatWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, sendHeartbeatProvider.get());
  }

  public static HeartbeatWorker_Factory create(
      Provider<SendHeartbeatUseCase> sendHeartbeatProvider) {
    return new HeartbeatWorker_Factory(sendHeartbeatProvider);
  }

  public static HeartbeatWorker newInstance(Context context, WorkerParameters params,
      SendHeartbeatUseCase sendHeartbeat) {
    return new HeartbeatWorker(context, params, sendHeartbeat);
  }
}
