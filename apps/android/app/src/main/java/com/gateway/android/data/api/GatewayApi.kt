package com.gateway.android.data.api

import retrofit2.Response
import retrofit2.http.*

interface GatewayApi {

    // ── Auth ──

    @POST("v1/auth/register")
    suspend fun register(@Body body: Map<String, String>): Response<MerchantLoginResponse>

    @POST("v1/auth/login")
    suspend fun login(@Body body: Map<String, String>): Response<MerchantLoginResponse>

    // ── Account ──

    @GET("v1/account")
    suspend fun getAccount(): Response<AccountResponse>

    @PATCH("v1/account")
    suspend fun updateAccount(@Body body: Map<String, String>): Response<AccountResponse>

    // ── Payment links & payments ──

    @POST("v1/payment-links")
    suspend fun createPaymentLink(@Body body: Map<String, @JvmSuppressWildcards Any>): Response<PaymentLinkResponse>

    @GET("v1/payment-links")
    suspend fun listPaymentLinks(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("status") status: String? = null,
    ): Response<PaymentListResponse>

    @GET("v1/payment-links/{id}")
    suspend fun getPaymentLink(@Path("id") id: String): Response<PaymentResponse>

    @GET("v1/payments")
    suspend fun listPayments(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("status") status: String? = null,
    ): Response<PaymentListResponse>

    @GET("v1/payments/{id}")
    suspend fun getPayment(@Path("id") id: String): Response<PaymentResponse>

    // ── API keys ──

    @GET("v1/api-keys")
    suspend fun listApiKeys(): Response<ApiKeyListResponse>

    @POST("v1/api-keys")
    suspend fun createApiKey(@Body body: Map<String, String>): Response<ApiKeyCreateResponse>

    // ── Webhooks ──

    @POST("v1/webhooks/test")
    suspend fun testWebhook(): Response<MessageResponse>

    @GET("v1/webhooks/logs")
    suspend fun listWebhookLogs(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
    ): Response<WebhookLogListResponse>

    // ── Health ──

    @GET("v1/health/summary")
    suspend fun getHealthSummary(): Response<HealthSummaryResponse>

    // ── Devices ──

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
    ): Response<HealthReportResponse>

    @PATCH("v1/devices/{id}/status")
    suspend fun updateDeviceStatus(
        @Path("id") deviceId: String,
        @Body body: Map<String, Boolean>,
    ): Response<Map<String, Any>>

    // ── Evidence ──

    @POST("v1/evidence")
    suspend fun uploadEvidence(@Body body: Map<String, @JvmSuppressWildcards Any>): Response<EvidenceUploadResponse>
}

// ── Response models ──

data class MerchantLoginResponse(val data: MerchantLoginData)

data class MerchantLoginData(
    val merchant: MerchantInfo,
    val access_token: String,
    val refresh_token: String,
)

data class MerchantInfo(
    val id: String,
    val name: String,
    val email: String,
    val upi_id: String?,
)

data class AccountResponse(val data: AccountData)

data class AccountData(
    val id: String,
    val name: String,
    val email: String,
    val upi_id: String?,
    val webhook_url: String?,
    val logo_url: String?,
    val status: String,
)

data class PaymentLinkResponse(val data: PaymentLinkData)

data class PaymentLinkData(
    val id: String,
    val payment_link: String,
    val qr_url: String,
    val upi_intent: String,
    val amount: Int,
    val currency: String,
    val status: String,
    val order_id: String,
    val expires_at: String,
    val created_at: String,
)

data class PaymentListResponse(val data: List<PaymentData>, val pagination: PaginationData)

data class PaymentResponse(val data: PaymentData)

data class PaymentData(
    val id: String,
    val amount: Int,
    val currency: String,
    val status: String,
    val order_id: String,
    val customer_name: String?,
    val payment_link: String?,
    val qr_url: String?,
    val upi_intent: String?,
    val expires_at: String?,
    val paid_at: String?,
    val created_at: String,
)

data class PaginationData(
    val page: Int,
    val limit: Int,
    val total: Int,
    val total_pages: Int,
)

data class ApiKeyListResponse(val data: List<ApiKeyData>)

data class ApiKeyCreateResponse(val data: ApiKeyCreateData)

data class ApiKeyData(
    val id: String,
    val key_prefix: String,
    val label: String?,
    val is_active: Boolean,
    val last_used_at: String?,
    val created_at: String,
)

data class ApiKeyCreateData(
    val key: String,
    val prefix: String,
    val label: String?,
)

data class MessageResponse(val data: Map<String, String>)

data class WebhookLogListResponse(val data: List<WebhookLogData>, val pagination: PaginationData)

data class WebhookLogData(
    val id: String,
    val payment_id: String,
    val delivered: Boolean,
    val response_status: Int?,
    val error: String?,
    val created_at: String,
)

data class HealthSummaryResponse(val data: HealthSummaryData)

data class HealthSummaryData(
    val health_score: Int,
    val health_level: String,
    val active_devices: Int,
    val online_devices: Int,
)

data class HealthReportResponse(val data: HealthReportData)

data class HealthReportData(val health_score: Int)

data class DeviceRegisterResponse(val data: DeviceRegisterData)

data class DeviceRegisterData(
    val device_id: String,
    val device_secret: String,
    val status: String,
    val token: String,
)

data class EvidenceUploadResponse(val data: EvidenceData)

data class EvidenceData(val id: String)
