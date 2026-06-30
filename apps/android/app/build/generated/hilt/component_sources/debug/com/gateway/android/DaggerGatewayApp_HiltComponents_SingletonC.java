package com.gateway.android;

import android.app.Activity;
import android.app.Service;
import android.content.SharedPreferences;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.gateway.android.data.api.GatewayApi;
import com.gateway.android.data.db.AppDatabase;
import com.gateway.android.data.db.EvidenceDao;
import com.gateway.android.data.repo.AuthRepository;
import com.gateway.android.data.repo.DeviceRepository;
import com.gateway.android.data.repo.EvidenceRepository;
import com.gateway.android.data.repo.MerchantRepository;
import com.gateway.android.data.repo.OnlineStateRepository;
import com.gateway.android.di.AppModule_ProvideDatabaseFactory;
import com.gateway.android.di.AppModule_ProvideEvidenceDaoFactory;
import com.gateway.android.di.AppModule_ProvideGatewayApiFactory;
import com.gateway.android.di.AppModule_ProvideNotificationParserFactory;
import com.gateway.android.di.AppModule_ProvideOkHttpClientFactory;
import com.gateway.android.di.AppModule_ProvideSharedPreferencesFactory;
import com.gateway.android.domain.parser.NotificationParser;
import com.gateway.android.service.ForegroundService;
import com.gateway.android.service.ForegroundService_MembersInjector;
import com.gateway.android.service.NotificationListenerService;
import com.gateway.android.service.NotificationListenerService_MembersInjector;
import com.gateway.android.ui.MainActivity;
import com.gateway.android.ui.MainActivity_MembersInjector;
import com.gateway.android.ui.developer.DeveloperViewModel;
import com.gateway.android.ui.developer.DeveloperViewModel_HiltModules_KeyModule_ProvideFactory;
import com.gateway.android.ui.feed.PaymentFeedViewModel;
import com.gateway.android.ui.feed.PaymentFeedViewModel_HiltModules_KeyModule_ProvideFactory;
import com.gateway.android.ui.health.DeviceHealthViewModel;
import com.gateway.android.ui.health.DeviceHealthViewModel_HiltModules_KeyModule_ProvideFactory;
import com.gateway.android.ui.home.HomeViewModel;
import com.gateway.android.ui.home.HomeViewModel_HiltModules_KeyModule_ProvideFactory;
import com.gateway.android.ui.links.CreateLinkViewModel;
import com.gateway.android.ui.links.CreateLinkViewModel_HiltModules_KeyModule_ProvideFactory;
import com.gateway.android.ui.links.PaymentLinksViewModel;
import com.gateway.android.ui.links.PaymentLinksViewModel_HiltModules_KeyModule_ProvideFactory;
import com.gateway.android.ui.login.AuthViewModel;
import com.gateway.android.ui.login.AuthViewModel_HiltModules_KeyModule_ProvideFactory;
import com.gateway.android.ui.payments.PaymentsViewModel;
import com.gateway.android.ui.payments.PaymentsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.gateway.android.ui.settings.SettingsViewModel;
import com.gateway.android.ui.settings.SettingsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.gateway.android.ui.setup.UpiSetupViewModel;
import com.gateway.android.ui.setup.UpiSetupViewModel_HiltModules_KeyModule_ProvideFactory;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SetBuilder;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DaggerGatewayApp_HiltComponents_SingletonC {
  private DaggerGatewayApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public GatewayApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements GatewayApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public GatewayApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements GatewayApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public GatewayApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements GatewayApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public GatewayApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements GatewayApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public GatewayApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements GatewayApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public GatewayApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements GatewayApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public GatewayApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements GatewayApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public GatewayApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends GatewayApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends GatewayApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends GatewayApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends GatewayApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return SetBuilder.<String>newSetBuilder(10).add(AuthViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(CreateLinkViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(DeveloperViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(DeviceHealthViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(HomeViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(PaymentFeedViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(PaymentLinksViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(PaymentsViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(SettingsViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(UpiSetupViewModel_HiltModules_KeyModule_ProvideFactory.provide()).build();
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @CanIgnoreReturnValue
    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectAuthRepository(instance, singletonCImpl.authRepositoryProvider.get());
      return instance;
    }
  }

  private static final class ViewModelCImpl extends GatewayApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<CreateLinkViewModel> createLinkViewModelProvider;

    private Provider<DeveloperViewModel> developerViewModelProvider;

    private Provider<DeviceHealthViewModel> deviceHealthViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<PaymentFeedViewModel> paymentFeedViewModelProvider;

    private Provider<PaymentLinksViewModel> paymentLinksViewModelProvider;

    private Provider<PaymentsViewModel> paymentsViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<UpiSetupViewModel> upiSetupViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.createLinkViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.developerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.deviceHealthViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.paymentFeedViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.paymentLinksViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.paymentsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.upiSetupViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
    }

    @Override
    public Map<String, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(10).put("com.gateway.android.ui.login.AuthViewModel", ((Provider) authViewModelProvider)).put("com.gateway.android.ui.links.CreateLinkViewModel", ((Provider) createLinkViewModelProvider)).put("com.gateway.android.ui.developer.DeveloperViewModel", ((Provider) developerViewModelProvider)).put("com.gateway.android.ui.health.DeviceHealthViewModel", ((Provider) deviceHealthViewModelProvider)).put("com.gateway.android.ui.home.HomeViewModel", ((Provider) homeViewModelProvider)).put("com.gateway.android.ui.feed.PaymentFeedViewModel", ((Provider) paymentFeedViewModelProvider)).put("com.gateway.android.ui.links.PaymentLinksViewModel", ((Provider) paymentLinksViewModelProvider)).put("com.gateway.android.ui.payments.PaymentsViewModel", ((Provider) paymentsViewModelProvider)).put("com.gateway.android.ui.settings.SettingsViewModel", ((Provider) settingsViewModelProvider)).put("com.gateway.android.ui.setup.UpiSetupViewModel", ((Provider) upiSetupViewModelProvider)).build();
    }

    @Override
    public Map<String, Object> getHiltViewModelAssistedMap() {
      return Collections.<String, Object>emptyMap();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.gateway.android.ui.login.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.authRepositoryProvider.get(), singletonCImpl.deviceRepositoryProvider.get());

          case 1: // com.gateway.android.ui.links.CreateLinkViewModel 
          return (T) new CreateLinkViewModel(singletonCImpl.merchantRepositoryProvider.get());

          case 2: // com.gateway.android.ui.developer.DeveloperViewModel 
          return (T) new DeveloperViewModel(singletonCImpl.merchantRepositoryProvider.get());

          case 3: // com.gateway.android.ui.health.DeviceHealthViewModel 
          return (T) new DeviceHealthViewModel(singletonCImpl.deviceRepositoryProvider.get(), singletonCImpl.merchantRepositoryProvider.get(), singletonCImpl.onlineStateRepositoryProvider.get());

          case 4: // com.gateway.android.ui.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.authRepositoryProvider.get(), singletonCImpl.merchantRepositoryProvider.get(), singletonCImpl.deviceRepositoryProvider.get(), singletonCImpl.onlineStateRepositoryProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.gateway.android.ui.feed.PaymentFeedViewModel 
          return (T) new PaymentFeedViewModel(singletonCImpl.evidenceRepositoryProvider.get());

          case 6: // com.gateway.android.ui.links.PaymentLinksViewModel 
          return (T) new PaymentLinksViewModel(singletonCImpl.merchantRepositoryProvider.get());

          case 7: // com.gateway.android.ui.payments.PaymentsViewModel 
          return (T) new PaymentsViewModel(singletonCImpl.merchantRepositoryProvider.get());

          case 8: // com.gateway.android.ui.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.authRepositoryProvider.get(), singletonCImpl.merchantRepositoryProvider.get(), singletonCImpl.onlineStateRepositoryProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // com.gateway.android.ui.setup.UpiSetupViewModel 
          return (T) new UpiSetupViewModel(singletonCImpl.merchantRepositoryProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends GatewayApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends GatewayApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectForegroundService(ForegroundService foregroundService) {
      injectForegroundService2(foregroundService);
    }

    @Override
    public void injectNotificationListenerService(
        NotificationListenerService notificationListenerService) {
      injectNotificationListenerService2(notificationListenerService);
    }

    @CanIgnoreReturnValue
    private ForegroundService injectForegroundService2(ForegroundService instance) {
      ForegroundService_MembersInjector.injectDeviceRepo(instance, singletonCImpl.deviceRepositoryProvider.get());
      ForegroundService_MembersInjector.injectAuthRepo(instance, singletonCImpl.authRepositoryProvider.get());
      ForegroundService_MembersInjector.injectEvidenceRepo(instance, singletonCImpl.evidenceRepositoryProvider.get());
      ForegroundService_MembersInjector.injectOnlineStateRepo(instance, singletonCImpl.onlineStateRepositoryProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private NotificationListenerService injectNotificationListenerService2(
        NotificationListenerService instance) {
      NotificationListenerService_MembersInjector.injectEvidenceRepo(instance, singletonCImpl.evidenceRepositoryProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends GatewayApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<SharedPreferences> provideSharedPreferencesProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<GatewayApi> provideGatewayApiProvider;

    private Provider<AuthRepository> authRepositoryProvider;

    private Provider<DeviceRepository> deviceRepositoryProvider;

    private Provider<MerchantRepository> merchantRepositoryProvider;

    private Provider<OnlineStateRepository> onlineStateRepositoryProvider;

    private Provider<AppDatabase> provideDatabaseProvider;

    private Provider<EvidenceDao> provideEvidenceDaoProvider;

    private Provider<NotificationParser> provideNotificationParserProvider;

    private Provider<EvidenceRepository> evidenceRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideSharedPreferencesProvider = DoubleCheck.provider(new SwitchingProvider<SharedPreferences>(singletonCImpl, 3));
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 2));
      this.provideGatewayApiProvider = DoubleCheck.provider(new SwitchingProvider<GatewayApi>(singletonCImpl, 1));
      this.authRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepository>(singletonCImpl, 0));
      this.deviceRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<DeviceRepository>(singletonCImpl, 4));
      this.merchantRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<MerchantRepository>(singletonCImpl, 5));
      this.onlineStateRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<OnlineStateRepository>(singletonCImpl, 6));
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 9));
      this.provideEvidenceDaoProvider = DoubleCheck.provider(new SwitchingProvider<EvidenceDao>(singletonCImpl, 8));
      this.provideNotificationParserProvider = DoubleCheck.provider(new SwitchingProvider<NotificationParser>(singletonCImpl, 10));
      this.evidenceRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<EvidenceRepository>(singletonCImpl, 7));
    }

    @Override
    public void injectGatewayApp(GatewayApp gatewayApp) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.gateway.android.data.repo.AuthRepository 
          return (T) new AuthRepository(singletonCImpl.provideGatewayApiProvider.get(), singletonCImpl.provideSharedPreferencesProvider.get());

          case 1: // com.gateway.android.data.api.GatewayApi 
          return (T) AppModule_ProvideGatewayApiFactory.provideGatewayApi(singletonCImpl.provideOkHttpClientProvider.get());

          case 2: // okhttp3.OkHttpClient 
          return (T) AppModule_ProvideOkHttpClientFactory.provideOkHttpClient(singletonCImpl.provideSharedPreferencesProvider.get());

          case 3: // android.content.SharedPreferences 
          return (T) AppModule_ProvideSharedPreferencesFactory.provideSharedPreferences(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.gateway.android.data.repo.DeviceRepository 
          return (T) new DeviceRepository(singletonCImpl.provideGatewayApiProvider.get(), singletonCImpl.authRepositoryProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.gateway.android.data.repo.MerchantRepository 
          return (T) new MerchantRepository(singletonCImpl.provideGatewayApiProvider.get(), singletonCImpl.authRepositoryProvider.get());

          case 6: // com.gateway.android.data.repo.OnlineStateRepository 
          return (T) new OnlineStateRepository(singletonCImpl.provideGatewayApiProvider.get(), singletonCImpl.authRepositoryProvider.get(), singletonCImpl.provideSharedPreferencesProvider.get());

          case 7: // com.gateway.android.data.repo.EvidenceRepository 
          return (T) new EvidenceRepository(singletonCImpl.provideGatewayApiProvider.get(), singletonCImpl.provideEvidenceDaoProvider.get(), singletonCImpl.provideNotificationParserProvider.get(), singletonCImpl.authRepositoryProvider.get());

          case 8: // com.gateway.android.data.db.EvidenceDao 
          return (T) AppModule_ProvideEvidenceDaoFactory.provideEvidenceDao(singletonCImpl.provideDatabaseProvider.get());

          case 9: // com.gateway.android.data.db.AppDatabase 
          return (T) AppModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 10: // com.gateway.android.domain.parser.NotificationParser 
          return (T) AppModule_ProvideNotificationParserFactory.provideNotificationParser();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
