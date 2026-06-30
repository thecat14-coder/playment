package com.gateway.android.data.repo;

import android.content.SharedPreferences;
import com.gateway.android.data.api.GatewayApi;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\u000bJ$\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u00132\u0006\u0010\u0014\u001a\u00020\u000bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000eR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0018"}, d2 = {"Lcom/gateway/android/data/repo/OnlineStateRepository;", "", "api", "Lcom/gateway/android/data/api/GatewayApi;", "authRepo", "Lcom/gateway/android/data/repo/AuthRepository;", "preferences", "Landroid/content/SharedPreferences;", "(Lcom/gateway/android/data/api/GatewayApi;Lcom/gateway/android/data/repo/AuthRepository;Landroid/content/SharedPreferences;)V", "_isOnline", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "isOnline", "Lkotlinx/coroutines/flow/StateFlow;", "()Lkotlinx/coroutines/flow/StateFlow;", "clear", "", "isMerchantOnline", "setOnline", "Lkotlin/Result;", "online", "setOnline-gIAlu-s", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class OnlineStateRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.api.GatewayApi api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.repo.AuthRepository authRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences preferences = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isOnline = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isOnline = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_IS_ONLINE = "merchant_is_online";
    @org.jetbrains.annotations.NotNull()
    public static final com.gateway.android.data.repo.OnlineStateRepository.Companion Companion = null;
    
    @javax.inject.Inject()
    public OnlineStateRepository(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.api.GatewayApi api, @org.jetbrains.annotations.NotNull()
    com.gateway.android.data.repo.AuthRepository authRepo, @org.jetbrains.annotations.NotNull()
    android.content.SharedPreferences preferences) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isOnline() {
        return null;
    }
    
    public final boolean isMerchantOnline() {
        return false;
    }
    
    public final void clear() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/gateway/android/data/repo/OnlineStateRepository$Companion;", "", "()V", "KEY_IS_ONLINE", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}