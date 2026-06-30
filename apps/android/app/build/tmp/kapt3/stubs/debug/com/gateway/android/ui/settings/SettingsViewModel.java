package com.gateway.android.ui.settings;

import androidx.lifecycle.ViewModel;
import com.gateway.android.data.repo.AuthRepository;
import com.gateway.android.data.repo.MerchantRepository;
import com.gateway.android.data.repo.OnlineStateRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import android.content.Context;
import android.content.Intent;
import com.gateway.android.service.ForegroundService;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B)\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0001\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0006\u0010\u0012\u001a\u00020\u0013J\u0014\u0010\u0014\u001a\u00020\u00132\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00130\u0016J\u0006\u0010\u0017\u001a\u00020\u0013J\u000e\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001c\u001a\u00020\u001aR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001d"}, d2 = {"Lcom/gateway/android/ui/settings/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "authRepo", "Lcom/gateway/android/data/repo/AuthRepository;", "merchantRepo", "Lcom/gateway/android/data/repo/MerchantRepository;", "onlineStateRepo", "Lcom/gateway/android/data/repo/OnlineStateRepository;", "context", "Landroid/content/Context;", "(Lcom/gateway/android/data/repo/AuthRepository;Lcom/gateway/android/data/repo/MerchantRepository;Lcom/gateway/android/data/repo/OnlineStateRepository;Landroid/content/Context;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/gateway/android/ui/settings/SettingsUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "load", "", "logout", "onLoggedOut", "Lkotlin/Function0;", "save", "updateName", "name", "", "updateUpi", "upiId", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.AuthRepository authRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.MerchantRepository merchantRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.OnlineStateRepository onlineStateRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gateway.android.ui.settings.SettingsUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.gateway.android.ui.settings.SettingsUiState> state = null;
    
    @javax.inject.Inject()
    public SettingsViewModel(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.AuthRepository authRepo, @org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.MerchantRepository merchantRepo, @org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.OnlineStateRepository onlineStateRepo, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.gateway.android.ui.settings.SettingsUiState> getState() {
        return null;
    }
    
    public final void load() {
    }
    
    public final void updateName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void updateUpi(@org.jetbrains.annotations.NotNull()
    java.lang.String upiId) {
    }
    
    public final void save() {
    }
    
    public final void logout(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLoggedOut) {
    }
}