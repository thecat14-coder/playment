package com.gateway.android.di;

import com.gateway.android.data.db.AppDatabase;
import com.gateway.android.data.db.EvidenceDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AppModule_ProvideEvidenceDaoFactory implements Factory<EvidenceDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideEvidenceDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public EvidenceDao get() {
    return provideEvidenceDao(dbProvider.get());
  }

  public static AppModule_ProvideEvidenceDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideEvidenceDaoFactory(dbProvider);
  }

  public static EvidenceDao provideEvidenceDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideEvidenceDao(db));
  }
}
