package com.gateway.android.data.api;

import retrofit2.Response;
import retrofit2.http.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J*\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0014\b\u0001\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006H\u00a7@\u00a2\u0006\u0002\u0010\bJ/\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\u0019\b\u0001\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\t\u0012\u00070\u0001\u00a2\u0006\u0002\b\u000b0\u0006H\u00a7@\u00a2\u0006\u0002\u0010\bJE\u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00060\u00032\b\b\u0001\u0010\r\u001a\u00020\u00072\u0019\b\u0001\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\t\u0012\u00070\u0001\u00a2\u0006\u0002\b\u000b0\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ@\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00060\u00032\b\b\u0001\u0010\r\u001a\u00020\u00072\u0014\b\u0001\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00100\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ@\u0010\u0011\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u00060\u00032\b\b\u0001\u0010\r\u001a\u00020\u00072\u0014\b\u0001\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00100\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ/\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00032\u0019\b\u0001\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\t\u0012\u00070\u0001\u00a2\u0006\u0002\b\u000b0\u0006H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\u0014"}, d2 = {"Lcom/gateway/android/data/api/GatewayApi;", "", "deviceLogin", "Lretrofit2/Response;", "Lcom/gateway/android/data/api/DeviceLoginResponse;", "body", "", "", "(Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerDevice", "Lcom/gateway/android/data/api/DeviceRegisterResponse;", "Lkotlin/jvm/JvmSuppressWildcards;", "sendHealthReport", "deviceId", "(Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendHeartbeat", "", "updateDeviceStatus", "uploadEvidence", "Lcom/gateway/android/data/api/EvidenceUploadResponse;", "app_debug"})
public abstract interface GatewayApi {
    
    @retrofit2.http.POST(value = "v1/auth/device-login")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deviceLogin(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.String> body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.gateway.android.data.api.DeviceLoginResponse>> $completion);
    
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
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.Map<java.lang.String, java.lang.Object>>> $completion);
    
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
}