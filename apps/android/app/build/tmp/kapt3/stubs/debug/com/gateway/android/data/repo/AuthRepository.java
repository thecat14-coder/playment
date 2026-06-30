package com.gateway.android.data.repo;

import android.content.SharedPreferences;
import com.gateway.android.data.api.AuthInterceptor;
import com.gateway.android.data.api.GatewayApi;
import com.gateway.android.data.api.MerchantInfo;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u0000 ,2\u00020\u0001:\u0001,B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u0004\u0018\u00010\bJ\b\u0010\t\u001a\u0004\u0018\u00010\bJ\b\u0010\n\u001a\u0004\u0018\u00010\bJ\u0006\u0010\u000b\u001a\u00020\bJ\b\u0010\f\u001a\u0004\u0018\u00010\bJ\b\u0010\r\u001a\u0004\u0018\u00010\bJ\b\u0010\u000e\u001a\u0004\u0018\u00010\bJ\b\u0010\u000f\u001a\u0004\u0018\u00010\bJ\b\u0010\u0010\u001a\u0004\u0018\u00010\bJ\u0006\u0010\u0011\u001a\u00020\u0012J,\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0006\u0010\u001a\u001a\u00020\u0015J\u0006\u0010\u001b\u001a\u00020\u0012J>\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u001d\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\b2\b\u0010\u001e\u001a\u0004\u0018\u00010\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001f\u0010 J\u001e\u0010!\u001a\u00020\u00152\u0006\u0010\"\u001a\u00020\b2\u0006\u0010#\u001a\u00020\b2\u0006\u0010$\u001a\u00020\bJ\u001e\u0010%\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\'2\u0006\u0010(\u001a\u00020\b2\u0006\u0010)\u001a\u00020\bJ\u000e\u0010*\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\bJ\u000e\u0010+\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006-"}, d2 = {"Lcom/gateway/android/data/repo/AuthRepository;", "", "api", "Lcom/gateway/android/data/api/GatewayApi;", "preferences", "Landroid/content/SharedPreferences;", "(Lcom/gateway/android/data/api/GatewayApi;Landroid/content/SharedPreferences;)V", "getDeviceId", "", "getDeviceSecret", "getDeviceToken", "getDeviceUuid", "getMerchantEmail", "getMerchantId", "getMerchantName", "getMerchantToken", "getMerchantUpiId", "isLoggedIn", "", "login", "Lkotlin/Result;", "", "email", "password", "login-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "needsUpiSetup", "register", "name", "upiId", "register-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveDeviceAuth", "deviceId", "deviceSecret", "deviceToken", "saveMerchantSession", "merchant", "Lcom/gateway/android/data/api/MerchantInfo;", "accessToken", "refreshToken", "updateMerchantName", "updateMerchantUpi", "Companion", "app_debug"})
public final class AuthRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.gateway.android.data.api.GatewayApi api = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences preferences = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DEVICE_ID = "device_id";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DEVICE_SECRET = "device_secret";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_MERCHANT_ID = "merchant_id";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_MERCHANT_NAME = "merchant_name";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_MERCHANT_EMAIL = "merchant_email";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_MERCHANT_UPI = "merchant_upi";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DEVICE_UUID = "device_uuid";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_REFRESH_TOKEN = "refresh_token";
    @org.jetbrains.annotations.NotNull()
    public static final com.gateway.android.data.repo.AuthRepository.Companion Companion = null;
    
    @javax.inject.Inject()
    public AuthRepository(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.api.GatewayApi api, @org.jetbrains.annotations.NotNull()
    android.content.SharedPreferences preferences) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getMerchantToken() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDeviceToken() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDeviceId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDeviceSecret() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getMerchantId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getMerchantName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getMerchantEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getMerchantUpiId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDeviceUuid() {
        return null;
    }
    
    public final void saveMerchantSession(@org.jetbrains.annotations.NotNull()
    com.gateway.android.data.api.MerchantInfo merchant, @org.jetbrains.annotations.NotNull()
    java.lang.String accessToken, @org.jetbrains.annotations.NotNull()
    java.lang.String refreshToken) {
    }
    
    public final void saveDeviceAuth(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceSecret, @org.jetbrains.annotations.NotNull()
    java.lang.String deviceToken) {
    }
    
    public final void updateMerchantUpi(@org.jetbrains.annotations.NotNull()
    java.lang.String upiId) {
    }
    
    public final void updateMerchantName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final boolean needsUpiSetup() {
        return false;
    }
    
    public final boolean isLoggedIn() {
        return false;
    }
    
    public final void logout() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/gateway/android/data/repo/AuthRepository$Companion;", "", "()V", "KEY_DEVICE_ID", "", "KEY_DEVICE_SECRET", "KEY_DEVICE_UUID", "KEY_MERCHANT_EMAIL", "KEY_MERCHANT_ID", "KEY_MERCHANT_NAME", "KEY_MERCHANT_UPI", "KEY_REFRESH_TOKEN", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}