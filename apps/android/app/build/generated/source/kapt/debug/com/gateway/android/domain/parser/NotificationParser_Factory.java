package com.gateway.android.domain.parser;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NotificationParser_Factory implements Factory<NotificationParser> {
  @Override
  public NotificationParser get() {
    return newInstance();
  }

  public static NotificationParser_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static NotificationParser newInstance() {
    return new NotificationParser();
  }

  private static final class InstanceHolder {
    private static final NotificationParser_Factory INSTANCE = new NotificationParser_Factory();
  }
}
