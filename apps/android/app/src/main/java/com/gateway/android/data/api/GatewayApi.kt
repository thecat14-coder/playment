package com.gateway.android.data.api

import retrofit2.Response
import retrofit2.http.*

interface GatewayApi {

    @POST("v1/auth/login")
    suspend fun login(@Body body: Map<String, String>): Response<MerchantLoginResponse>

    @POST("v1/devices/register")
    suspend fun registerDevice(@Body body: Map<String, @JvmSuppressWildcards Any>): Response<DeviceRegisterResponse>

    @POST("v1/devices/{id}/heartbeat")
    suspend fun sendHeartbeat(
        @Path("id") deviceId: String,
        @Body body: Map<String, Boolean>,
    ): Response<Map<String, Any>>

    @POST("v1/devices/{id}/health")
    suspend fun sendHealthReport(
        @Path("id") deviceId: String,
        @Body body: Map<String, @JvmSuppressWildcards Any>,
    ): Response<Map<String, Any>>

    @PATCH("v1/devices/{id}/status")
    suspend fun updateDeviceStatus(
        @Path("id") deviceId: String,
        @Body body: Map<String, Boolean>,
    ): Response<Map<String, Any>>

    @POST("v1/evidence")
    suspend fun uploadEvidence(@Body body: Map<String, @JvmSuppressWildcards Any>): Response<EvidenceUploadResponse>
}

data class MerchantLoginResponse(
    val data: MerchantLoginData,
)

data class MerchantLoginData(
    val merchant: MerchantInfo,
    val access_token: String,
    val refresh_token: String,
)

data class MerchantInfo(
    val id: String,
    val name: String,
    val email: String,
    val upi_id: String,
)

data class DeviceRegisterResponse(
    val data: DeviceRegisterData,
)

data class DeviceRegisterData(
    val device_id: String,
    val device_secret: String,
    val status: String,
    val token: String,
)

data class EvidenceUploadResponse(
    val data: EvidenceData,
)

data class EvidenceData(
    val id: String,
)
