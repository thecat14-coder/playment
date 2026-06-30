package com.gateway.android.ui.links;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.navigation.NavController;
import com.gateway.android.data.api.PaymentData;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a:\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u001a\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u0007\u001a\u0018\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u0010H\u0002\u001a\u0018\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u0010H\u0002\u00a8\u0006\u0012"}, d2 = {"PaymentLinkCard", "", "link", "Lcom/gateway/android/data/api/PaymentData;", "onCopy", "Lkotlin/Function0;", "onShare", "onClick", "PaymentLinksScreen", "navController", "Landroidx/navigation/NavController;", "viewModel", "Lcom/gateway/android/ui/links/PaymentLinksViewModel;", "copyLink", "context", "Landroid/content/Context;", "", "shareLink", "app_debug"})
public final class PaymentLinksScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void PaymentLinksScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull()
    com.gateway.android.ui.links.PaymentLinksViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PaymentLinkCard(com.gateway.android.data.api.PaymentData link, kotlin.jvm.functions.Function0<kotlin.Unit> onCopy, kotlin.jvm.functions.Function0<kotlin.Unit> onShare, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    private static final void copyLink(android.content.Context context, java.lang.String link) {
    }
    
    private static final void shareLink(android.content.Context context, java.lang.String link) {
    }
}