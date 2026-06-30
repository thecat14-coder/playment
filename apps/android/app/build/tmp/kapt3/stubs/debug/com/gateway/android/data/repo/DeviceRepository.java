package com.gateway.android.data.repo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import com.gateway.android.data.api.GatewayApi;
import com.gateway.android.domain.model.HealthStatus;
import com.gateway.android.service.ForegroundService;
import com.gateway.android.service.NotificationListenerService;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\u0006\u0010\r\u001a\u00020\u000eJ\b\u0010\u000f\u001a\u00020\u0010H\u0002J\u001c\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\n0\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J$\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\f0\u00122\u0006\u0010\u0016\u001a\u00020\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J,\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00122\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u001dJ$\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00122\u0006\u0010\u001f\u001a\u00020\u0010H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b \u0010!R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\""}, d2 = {"Lcom/gateway/android/data/repo/DeviceRepository;", "", "api", "Lcom/gateway/android/data/api/GatewayApi;", "authRepo", "Lcom/gateway/android/data/repo/AuthRepository;", "context", "Landroid/content/Context;", "(Lcom/gateway/android/data/api/GatewayApi;Lcom/gateway/android/data/repo/AuthRepository;Landroid/content/Context;)V", "getAppVersion", "", "getBatteryLevel", "", "getDeviceHealth", "Lcom/gateway/android/domain/model/HealthStatus;", "isNetworkConnected", "", "registerDevice", "Lkotlin/Result;", "registerDevice-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendHealthReport", "deviceId", "sendHealthReport-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendHeartbeat", "", "listenerRunning", "sendHeartbeat-0E7RQCE", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateDeviceStatus", "isOnline", "updateDeviceStatus-gIAlu-s", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class DeviceRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.api.GatewayApi api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.AuthRepository authRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    @javax.inject.Inject()
    public DeviceRepository(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.api.GatewayApi api, @org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.AuthRepository authRepo, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.gateway.android.domain.model.HealthStatus getDeviceHealth() {
        return null;
    }
    
    private final java.lang.String getAppVersion() {
        return null;
    }
    
    private final boolean isNetworkConnected() {
        return false;
    }
    
    private final int getBatteryLevel() {
        return 0;
    }
}