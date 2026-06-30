package com.gateway.android.data.repo;

import com.gateway.android.data.api.GatewayApi;
import com.gateway.android.data.db.EvidenceDao;
import com.gateway.android.domain.parser.NotificationParser;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class EvidenceRepository_Factory implements Factory<EvidenceRepository> {
  private final Provider<GatewayApi> apiProvider;

  private final Provider<EvidenceDao> evidenceDaoProvider;

  private final Provider<NotificationParser> parserProvider;

  private final Provider<AuthRepository> authRepoProvider;

  public EvidenceRepository_Factory(Provider<GatewayApi> apiProvider,
      Provider<EvidenceDao> evidenceDaoProvider, Provider<NotificationParser> parserProvider,
      Provider<AuthRepository> authRepoProvider) {
    this.apiProvider = apiProvider;
    this.evidenceDaoProvider = evidenceDaoProvider;
    this.parserProvider = parserProvider;
    this.authRepoProvider = authRepoProvider;
  }

  @Override
  public EvidenceRepository get() {
    return newInstance(apiProvider.get(), evidenceDaoProvider.get(), parserProvider.get(), authRepoProvider.get());
  }

  public static EvidenceRepository_Factory create(Provider<GatewayApi> apiProvider,
      Provider<EvidenceDao> evidenceDaoProvider, Provider<NotificationParser> parserProvider,
      Provider<AuthRepository> authRepoProvider) {
    return new EvidenceRepository_Factory(apiProvider, evidenceDaoProvider, parserProvider, authRepoProvider);
  }

  public static EvidenceRepository newInstance(GatewayApi api, EvidenceDao evidenceDao,
      NotificationParser parser, AuthRepository authRepo) {
    return new EvidenceRepository(api, evidenceDao, parser, authRepo);
  }
}
