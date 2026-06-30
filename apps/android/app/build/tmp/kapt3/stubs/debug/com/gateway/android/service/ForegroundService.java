package com.gateway.android.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import androidx.core.app.NotificationCompat;
import com.gateway.android.data.repo.AuthRepository;
import com.gateway.android.data.repo.DeviceRepository;
import com.gateway.android.data.repo.EvidenceRepository;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.*;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\b\u0007\u0018\u0000 *2\u00020\u0001:\u0001*B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\u0014\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010!\u001a\u00020\u001cH\u0016J\b\u0010\"\u001a\u00020\u001cH\u0016J\"\u0010#\u001a\u00020$2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020$H\u0016J\b\u0010\'\u001a\u00020\u001cH\u0002J\b\u0010(\u001a\u00020\u001cH\u0002J\b\u0010)\u001a\u00020\u001cH\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u00020\u00108\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006+"}, d2 = {"Lcom/gateway/android/service/ForegroundService;", "Landroid/app/Service;", "()V", "authRepo", "Lcom/gateway/android/data/repo/AuthRepository;", "getAuthRepo", "()Lcom/gateway/android/data/repo/AuthRepository;", "setAuthRepo", "(Lcom/gateway/android/data/repo/AuthRepository;)V", "deviceRepo", "Lcom/gateway/android/data/repo/DeviceRepository;", "getDeviceRepo", "()Lcom/gateway/android/data/repo/DeviceRepository;", "setDeviceRepo", "(Lcom/gateway/android/data/repo/DeviceRepository;)V", "evidenceRepo", "Lcom/gateway/android/data/repo/EvidenceRepository;", "getEvidenceRepo", "()Lcom/gateway/android/data/repo/EvidenceRepository;", "setEvidenceRepo", "(Lcom/gateway/android/data/repo/EvidenceRepository;)V", "isRunning", "", "scope", "Lkotlinx/coroutines/CoroutineScope;", "buildNotification", "Landroid/app/Notification;", "createNotificationChannel", "", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "", "flags", "startId", "startEvidenceRetryLoop", "startHealthReportLoop", "startHeartbeatLoop", "Companion", "app_debug"})
public final class ForegroundService extends android.app.Service {
    @javax.inject.Inject()
    public com.gateway.android.data.repo.DeviceRepository deviceRepo;
    @javax.inject.Inject()
    public com.gateway.android.data.repo.AuthRepository authRepo;
    @javax.inject.Inject()
    public com.gateway.android.data.repo.EvidenceRepository evidenceRepo;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    private boolean isRunning = false;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_ID = "gateway_foreground_service";
    private static final int NOTIFICATION_ID = 1;
    private static final long HEARTBEAT_INTERVAL_MS = 60000L;
    private static final long HEALTH_REPORT_INTERVAL_MS = 300000L;
    private static final long RETRY_INTERVAL_MS = 120000L;
    @org.jetbrains.annotations.NotNull()
    public static final com.gateway.android.service.ForegroundService.Companion Companion = null;
    
    public ForegroundService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.gateway.android.data.repo.DeviceRepository getDeviceRepo() {
        return null;
    }
    
    public final void setDeviceRepo(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.DeviceRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.gateway.android.data.repo.AuthRepository getAuthRepo() {
        return null;
    }
    
    public final void setAuthRepo(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.AuthRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.gateway.android.data.repo.EvidenceRepository getEvidenceRepo() {
        return null;
    }
    
    public final void setEvidenceRepo(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.EvidenceRepository p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    private final void startHeartbeatLoop() {
    }
    
    private final void startHealthReportLoop() {
    }
    
    private final void startEvidenceRetryLoop() {
    }
    
    private final void createNotificationChannel() {
    }
    
    private final android.app.Notification buildNotification() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/gateway/android/service/ForegroundService$Companion;", "", "()V", "CHANNEL_ID", "", "HEALTH_REPORT_INTERVAL_MS", "", "HEARTBEAT_INTERVAL_MS", "NOTIFICATION_ID", "", "RETRY_INTERVAL_MS", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}