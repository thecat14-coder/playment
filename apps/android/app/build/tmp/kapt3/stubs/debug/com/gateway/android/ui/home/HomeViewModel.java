package com.gateway.android.ui.home;

import android.content.Intent;
import androidx.lifecycle.ViewModel;
import com.gateway.android.data.api.PaymentData;
import com.gateway.android.data.repo.AuthRepository;
import com.gateway.android.data.repo.DeviceRepository;
import com.gateway.android.data.repo.MerchantRepository;
import com.gateway.android.data.repo.OnlineStateRepository;
import com.gateway.android.service.ForegroundService;
import com.gateway.android.service.NotificationListenerService;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import android.content.Context;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B1\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0001\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u001bJ\u0006\u0010\u001d\u001a\u00020\u001bR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u001e"}, d2 = {"Lcom/gateway/android/ui/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "authRepo", "Lcom/gateway/android/data/repo/AuthRepository;", "merchantRepo", "Lcom/gateway/android/data/repo/MerchantRepository;", "deviceRepo", "Lcom/gateway/android/data/repo/DeviceRepository;", "onlineStateRepo", "Lcom/gateway/android/data/repo/OnlineStateRepository;", "context", "Landroid/content/Context;", "(Lcom/gateway/android/data/repo/AuthRepository;Lcom/gateway/android/data/repo/MerchantRepository;Lcom/gateway/android/data/repo/DeviceRepository;Lcom/gateway/android/data/repo/OnlineStateRepository;Landroid/content/Context;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/gateway/android/ui/home/HomeUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "isToday", "", "iso", "", "today", "Ljava/time/LocalDate;", "logout", "", "refresh", "toggleOnline", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.AuthRepository authRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.MerchantRepository merchantRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.DeviceRepository deviceRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.OnlineStateRepository onlineStateRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gateway.android.ui.home.HomeUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.gateway.android.ui.home.HomeUiState> state = null;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.AuthRepository authRepo, @org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.MerchantRepository merchantRepo, @org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.DeviceRepository deviceRepo, @org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.OnlineStateRepository onlineStateRepo, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.gateway.android.ui.home.HomeUiState> getState() {
        return null;
    }
    
    public final void refresh() {
    }
    
    public final void toggleOnline() {
    }
    
    public final void logout() {
    }
    
    private final boolean isToday(java.lang.String iso, java.time.LocalDate today) {
        return false;
    }
}