package com.gateway.android.data.api;

import retrofit2.Response;
import retrofit2.http.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J*\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0014\b\u0001\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006H\u00a7@\u00a2\u0006\u0002\u0010\bJ/\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\u0019\b\u0001\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\t\u0012\u00070\u0001\u00a2\u0006\u0002\b\u000b0\u0006H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00032\b\b\u0001\u0010\u0013\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00120\u00032\b\b\u0001\u0010\u0013\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ4\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00032\b\b\u0003\u0010\u001a\u001a\u00020\u001b2\b\b\u0003\u0010\u001c\u001a\u00020\u001b2\n\b\u0003\u0010\u001d\u001a\u0004\u0018\u00010\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ4\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00190\u00032\b\b\u0003\u0010\u001a\u001a\u00020\u001b2\b\b\u0003\u0010\u001c\u001a\u00020\u001b2\n\b\u0003\u0010\u001d\u001a\u0004\u0018\u00010\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ(\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u00032\b\b\u0003\u0010\u001a\u001a\u00020\u001b2\b\b\u0003\u0010\u001c\u001a\u00020\u001bH\u00a7@\u00a2\u0006\u0002\u0010\"J*\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u00032\u0014\b\u0001\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006H\u00a7@\u00a2\u0006\u0002\u0010\bJ*\u0010%\u001a\b\u0012\u0004\u0012\u00020$0\u00032\u0014\b\u0001\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006H\u00a7@\u00a2\u0006\u0002\u0010\bJ/\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\u00032\u0019\b\u0001\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\t\u0012\u00070\u0001\u00a2\u0006\u0002\b\u000b0\u0006H\u00a7@\u00a2\u0006\u0002\u0010\bJ9\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u00032\b\b\u0001\u0010*\u001a\u00020\u00072\u0019\b\u0001\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\t\u0012\u00070\u0001\u00a2\u0006\u0002\b\u000b0\u0006H\u00a7@\u00a2\u0006\u0002\u0010+J@\u0010,\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00060\u00032\b\b\u0001\u0010*\u001a\u00020\u00072\u0014\b\u0001\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020-0\u0006H\u00a7@\u00a2\u0006\u0002\u0010+J\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ*\u00100\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\u0014\b\u0001\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006H\u00a7@\u00a2\u0006\u0002\u0010\bJ@\u00101\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00060\u00032\b\b\u0001\u0010*\u001a\u00020\u00072\u0014\b\u0001\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020-0\u0006H\u00a7@\u00a2\u0006\u0002\u0010+J/\u00102\u001a\b\u0012\u0004\u0012\u0002030\u00032\u0019\b\u0001\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\t\u0012\u00070\u0001\u00a2\u0006\u0002\b\u000b0\u0006H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u00064"}, d2 = {"Lcom/gateway/android/data/api/GatewayApi;", "", "createApiKey", "Lretrofit2/Response;", "Lcom/gateway/android/data/api/ApiKeyCreateResponse;", "body", "", "", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createPaymentLink", "Lcom/gateway/android/data/api/PaymentLinkResponse;", "Lkotlin/jvm/JvmSuppressWildcards;", "getAccount", "Lcom/gateway/android/data/api/AccountResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHealthSummary", "Lcom/gateway/android/data/api/HealthSummaryResponse;", "getPayment", "Lcom/gateway/android/data/api/PaymentResponse;", "id", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPaymentLink", "listApiKeys", "Lcom/gateway/android/data/api/ApiKeyListResponse;", "listPaymentLinks", "Lcom/gateway/android/data/api/PaymentListResponse;", "page", "", "limit", "status", "(IILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "listPayments", "listWebhookLogs", "Lcom/gateway/android/data/api/WebhookLogListResponse;", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "Lcom/gateway/android/data/api/MerchantLoginResponse;", "register", "registerDevice", "Lcom/gateway/android/data/api/DeviceRegisterResponse;", "sendHealthReport", "Lcom/gateway/android/data/api/HealthReportResponse;", "deviceId", "(Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendHeartbeat", "", "testWebhook", "Lcom/gateway/android/data/api/MessageResponse;", "updateAccount", "updateDeviceStatus", "uploadEvidence", "Lcom/gateway/android/data/api/EvidenceUploadResponse;", "app_debug"})
public abstract interface GatewayApi {
    
    @retrofit2.http.POST(value = "v1/auth/register")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object register(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.MerchantLoginResponse>> $completion);
    
    @retrofit2.http.POST(value = "v1/auth/login")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.MerchantLoginResponse>> $completion);
    
    @retrofit2.http.GET(value = "v1/account")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAccount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.AccountResponse>> $completion);
    
    @retrofit2.http.PATCH(value = "v1/account")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateAccount(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.AccountResponse>> $completion);
    
    @retrofit2.http.POST(value = "v1/payment-links")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createPaymentLink(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Object> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.PaymentLinkResponse>> $completion);
    
    @retrofit2.http.GET(value = "v1/payment-links")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object listPaymentLinks(@retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "status")
    @org.jetbrains.annotations.Nullable()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.PaymentListResponse>> $completion);
    
    @retrofit2.http.GET(value = "v1/payment-links/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPaymentLink(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.PaymentResponse>> $completion);
    
    @retrofit2.http.GET(value = "v1/payments")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object listPayments(@retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "status")
    @org.jetbrains.annotations.Nullable()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.PaymentListResponse>> $completion);
    
    @retrofit2.http.GET(value = "v1/payments/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPayment(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.PaymentResponse>> $completion);
    
    @retrofit2.http.GET(value = "v1/api-keys")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object listApiKeys(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.ApiKeyListResponse>> $completion);
    
    @retrofit2.http.POST(value = "v1/api-keys")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createApiKey(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.ApiKeyCreateResponse>> $completion);
    
    @retrofit2.http.POST(value = "v1/webhooks/test")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object testWebhook(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.MessageResponse>> $completion);
    
    @retrofit2.http.GET(value = "v1/webhooks/logs")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object listWebhookLogs(@retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "limit")
    int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.WebhookLogListResponse>> $completion);
    
    @retrofit2.http.GET(value = "v1/health/summary")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getHealthSummary(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.HealthSummaryResponse>> $completion);
    
    @retrofit2.http.POST(value = "v1/devices/register")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object registerDevice(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Object> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.DeviceRegisterResponse>> $completion);
    
    @retrofit2.http.POST(value = "v1/devices/{id}/heartbeat")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendHeartbeat(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Boolean> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.Map<java.lang.String, java.lang.Object>>> $completion);
    
    @retrofit2.http.POST(value = "v1/devices/{id}/health")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendHealthReport(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Object> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.HealthReportResponse>> $completion);
    
    @retrofit2.http.PATCH(value = "v1/devices/{id}/status")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateDeviceStatus(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String deviceId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Boolean> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.Map<java.lang.String, java.lang.Object>>> $completion);
    
    @retrofit2.http.POST(value = "v1/evidence")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object uploadEvidence(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Object> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.EvidenceUploadResponse>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}