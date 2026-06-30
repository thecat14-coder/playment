package com.gateway.android.data.repo

import com.gateway.android.data.api.*
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MerchantRepository @Inject constructor(
    private val api: GatewayApi,
    private val authRepo: AuthRepository,
) {
    suspend fun getAccount(): Result<AccountData> =
        unwrap { api.getAccount() }.map { it.data }

    suspend fun updateAccount(
        name: String? = null,
        upiId: String? = null,
        webhookUrl: String? = null,
    ): Result<AccountData> {
        val body = mutableMapOf<String, String>()
        name?.let { body["name"] = it }
        upiId?.let { body["upi_id"] = it }
        webhookUrl?.let { body["webhook_url"] = it }
        return unwrap { api.updateAccount(body) }.map { response ->
            if (upiId != null) authRepo.updateMerchantUpi(upiId)
            if (name != null) authRepo.updateMerchantName(name)
            response.data
        }
    }

    suspend fun createPaymentLink(
        amountPaise: Int,
        orderId: String,
        customerName: String?,
        expiresInSeconds: Int,
    ): Result<PaymentLinkData> {
        val body = mutableMapOf<String, Any>(
            "amount" to amountPaise,
            "currency" to "INR",
            "order_id" to orderId,
            "expires_in" to expiresInSeconds,
        )
        if (!customerName.isNullOrBlank()) {
            body["customer_name"] = customerName.trim()
        }
        return unwrap { api.createPaymentLink(body) }.map { it.data }
    }

    suspend fun listPaymentLinks(page: Int = 1, limit: Int = 20, status: String? = null): Result<PaymentListResponse> =
        unwrap { api.listPaymentLinks(page = page, limit = limit, status = status) }

    suspend fun listPayments(page: Int = 1, limit: Int = 20, status: String? = null): Result<PaymentListResponse> =
        unwrap { api.listPayments(page = page, limit = limit, status = status) }

    suspend fun getPayment(id: String): Result<PaymentData> =
        unwrap { api.getPayment(id) }.map { it.data }

    suspend fun listApiKeys(): Result<List<ApiKeyData>> =
        unwrap { api.listApiKeys() }.map { it.data }

    suspend fun createApiKey(label: String?): Result<ApiKeyCreateData> {
        val body = if (!label.isNullOrBlank()) mapOf("label" to label.trim()) else emptyMap()
        return unwrap { api.createApiKey(body) }.map { it.data }
    }

    suspend fun testWebhook(): Result<String> =
        unwrap { api.testWebhook() }.map { it.data["message"] ?: "Test webhook enqueued" }

    suspend fun getHealthSummary(): Result<HealthSummaryData> =
        unwrap { api.getHealthSummary() }.map { it.data }

    private suspend fun <T> unwrap(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
