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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a(\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\fH\u0003\u00a8\u0006\r"}, d2 = {"DeviceHealthScreen", "", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/gateway/android/ui/health/DeviceHealthViewModel;", "HealthRow", "label", "", "ok", "", "action", "Lkotlin/Function0;", "app_debug"})
public final class DeviceHealthScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void DeviceHealthScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    com.gateway.android.ui.health.DeviceHealthViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HealthRow(java.lang.String label, boolean ok, kotlin.jvm.functions.Function0<kotlin.Unit> action) {
    }
}