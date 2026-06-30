package com.gateway.android.di;

import com.gateway.android.domain.parser.NotificationParser;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideNotificationParserFactory implements Factory<NotificationParser> {
  @Override
  public NotificationParser get() {
    return provideNotificationParser();
  }

  public static AppModule_ProvideNotificationParserFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static NotificationParser provideNotificationParser() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideNotificationParser());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideNotificationParserFactory INSTANCE = new AppModule_ProvideNotificationParserFactory();
  }
}
