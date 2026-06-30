package com.gateway.android.service;

import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.gateway.android.data.repo.EvidenceRepository;
import dagger.hilt.android.AndroidEntryPoint;
import kotlinx.coroutines.Dispatchers;
import javax.inject.Inject;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\fH\u0016J\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0012\u0010\u0011\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/gateway/android/service/NotificationListenerService;", "Landroid/service/notification/NotificationListenerService;", "()V", "evidenceRepo", "Lcom/gateway/android/data/repo/EvidenceRepository;", "getEvidenceRepo", "()Lcom/gateway/android/data/repo/EvidenceRepository;", "setEvidenceRepo", "(Lcom/gateway/android/data/repo/EvidenceRepository;)V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "onListenerConnected", "", "onListenerDisconnected", "onNotificationPosted", "sbn", "Landroid/service/notification/StatusBarNotification;", "onNotificationRemoved", "Companion", "app_debug"})
public final class NotificationListenerService extends android.service.notification.NotificationListenerService {
    @javax.inject.Inject()
    public com.gateway.android.data.repo.EvidenceRepository evidenceRepo;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "NotifListener";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String EXTRA_TITLE = "android.title";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String EXTRA_TEXT = "android.text";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String EXTRA_BIG_TEXT = "android.bigText";
    @kotlin.jvm.Volatile()
    private static volatile boolean isRunning = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.gateway.android.service.NotificationListenerService.Companion Companion = null;
    
    public NotificationListenerService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.gateway.android.data.repo.EvidenceRepository getEvidenceRepo() {
        return null;
    }
    
    public final void setEvidenceRepo(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.EvidenceRepository p0) {
    }
    
    @java.lang.Override()
    public void onNotificationPosted(@org.jetbrains.annotations.NotNull()
    android.service.notification.StatusBarNotification sbn) {
    }
    
    @java.lang.Override()
    public void onNotificationRemoved(@org.jetbrains.annotations.Nullable()
    android.service.notification.StatusBarNotification sbn) {
    }
    
    @java.lang.Override()
    public void onListenerConnected() {
    }
    
    @java.lang.Override()
    public void onListenerDisconnected() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\f"}, d2 = {"Lcom/gateway/android/service/NotificationListenerService$Companion;", "", "()V", "EXTRA_BIG_TEXT", "", "EXTRA_TEXT", "EXTRA_TITLE", "TAG", "<set-?>", "", "isRunning", "()Z", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final boolean isRunning() {
            return false;
        }
    }
}