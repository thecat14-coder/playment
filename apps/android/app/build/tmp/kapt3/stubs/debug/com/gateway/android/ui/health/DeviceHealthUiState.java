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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B/\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\tJ\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0007H\u00c6\u0003J3\u0010\u0013\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00072\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000e\u00a8\u0006\u0019"}, d2 = {"Lcom/gateway/android/ui/health/DeviceHealthUiState;", "", "health", "Lcom/gateway/android/domain/model/HealthStatus;", "healthScore", "", "isOnline", "", "isLoading", "(Lcom/gateway/android/domain/model/HealthStatus;IZZ)V", "getHealth", "()Lcom/gateway/android/domain/model/HealthStatus;", "getHealthScore", "()I", "()Z", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"})
public final class DeviceHealthUiState {
    @org.jetbrains.annotations.Nullable()
    private final com.gateway.android.domain.model.HealthStatus health = null;
    private final int healthScore = 0;
    private final boolean isOnline = false;
    private final boolean isLoading = false;
    
    public DeviceHealthUiState(@org.jetbrains.annotations.Nullable()
    com.gateway.android.domain.model.HealthStatus health, int healthScore, boolean isOnline, boolean isLoading) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.gateway.android.domain.model.HealthStatus getHealth() {
        return null;
    }
    
    public final int getHealthScore() {
        return 0;
    }
    
    public final boolean isOnline() {
        return false;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public DeviceHealthUiState() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.gateway.android.domain.model.HealthStatus component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.gateway.android.ui.health.DeviceHealthUiState copy(@org.jetbrains.annotations.Nullable()
    com.gateway.android.domain.model.HealthStatus health, int healthScore, boolean isOnline, boolean isLoading) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}