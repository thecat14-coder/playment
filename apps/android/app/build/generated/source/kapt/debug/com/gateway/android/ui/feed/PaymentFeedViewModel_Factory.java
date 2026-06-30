package com.gateway.android.ui.feed;

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
public final class PaymentFeedViewModel_Factory implements Factory<PaymentFeedViewModel> {
  private final Provider<EvidenceRepository> evidenceRepoProvider;

  public PaymentFeedViewModel_Factory(Provider<EvidenceRepository> evidenceRepoProvider) {
    this.evidenceRepoProvider = evidenceRepoProvider;
  }

  @Override
  public PaymentFeedViewModel get() {
    return newInstance(evidenceRepoProvider.get());
  }

  public static PaymentFeedViewModel_Factory create(
      Provider<EvidenceRepository> evidenceRepoProvider) {
    return new PaymentFeedViewModel_Factory(evidenceRepoProvider);
  }

  public static PaymentFeedViewModel newInstance(EvidenceRepository evidenceRepo) {
    return new PaymentFeedViewModel(evidenceRepo);
  }
}
