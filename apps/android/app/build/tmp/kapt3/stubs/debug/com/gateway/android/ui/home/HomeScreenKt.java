package com.gateway.android.ui.home;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.navigation.NavController;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0003\u001a\u001a\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001a&\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u00032\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000fH\u0003\u001a\"\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0003\u001a$\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0012H\u0003\u00a8\u0006\u001a"}, d2 = {"DeviceStatusCard", "", "listenerRunning", "", "healthScore", "", "HomeScreen", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/gateway/android/ui/home/HomeViewModel;", "OnlineToggleCard", "isOnline", "isLoading", "onToggle", "Lkotlin/Function0;", "StatCard", "title", "", "value", "modifier", "Landroidx/compose/ui/Modifier;", "StatusRow", "label", "ok", "detail", "app_debug"})
public final class HomeScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    com.gateway.android.ui.home.HomeViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void OnlineToggleCard(boolean isOnline, boolean isLoading, kotlin.jvm.functions.Function0<kotlin.Unit> onToggle) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatCard(java.lang.String title, java.lang.String value, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DeviceStatusCard(boolean listenerRunning, int healthScore) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatusRow(java.lang.String label, boolean ok, java.lang.String detail) {
    }
}