package com.gateway.android.ui.health;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import com.gateway.android.data.repo.DeviceRepository;
import com.gateway.android.data.repo.MerchantRepository;
import com.gateway.android.data.repo.OnlineStateRepository;
import com.gateway.android.domain.model.HealthStatus;
import com.gateway.android.service.ForegroundService;
import com.gateway.android.service.NotificationListenerService;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0010\u001a\u00020\u0011R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2 = {"Lcom/gateway/android/ui/health/DeviceHealthViewModel;", "Landroidx/lifecycle/ViewModel;", "deviceRepo", "Lcom/gateway/android/data/repo/DeviceRepository;", "merchantRepo", "Lcom/gateway/android/data/repo/MerchantRepository;", "onlineStateRepo", "Lcom/gateway/android/data/repo/OnlineStateRepository;", "(Lcom/gateway/android/data/repo/DeviceRepository;Lcom/gateway/android/data/repo/MerchantRepository;Lcom/gateway/android/data/repo/OnlineStateRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/gateway/android/ui/health/DeviceHealthUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "refresh", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DeviceHealthViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.DeviceRepository deviceRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.MerchantRepository merchantRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.OnlineStateRepository onlineStateRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gateway.android.ui.health.DeviceHealthUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.gateway.android.ui.health.DeviceHealthUiState> state = null;
    
    @javax.inject.Inject()
    public DeviceHealthViewModel(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.DeviceRepository deviceRepo, @org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.MerchantRepository merchantRepo, @org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.OnlineStateRepository onlineStateRepo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.gateway.android.ui.health.DeviceHealthUiState> getState() {
        return null;
    }
    
    public final void refresh() {
    }
}