package com.gateway.android.data.repo

import android.content.SharedPreferences
import com.gateway.android.data.api.AuthInterceptor
import com.gateway.android.data.api.GatewayApi
import com.gateway.android.data.api.MerchantInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: GatewayApi,
    private val preferences: SharedPreferences,
) {
    fun getMerchantToken(): String? = preferences.getString(AuthInterceptor.KEY_MERCHANT_TOKEN, null)

    fun getDeviceToken(): String? = preferences.getString(AuthInterceptor.KEY_DEVICE_TOKEN, null)

    fun getDeviceId(): String? = preferences.getString(KEY_DEVICE_ID, null)

    fun getDeviceSecret(): String? = preferences.getString(KEY_DEVICE_SECRET, null)

    fun getMerchantId(): String? = preferences.getString(KEY_MERCHANT_ID, null)

    fun getMerchantName(): String? = preferences.getString(KEY_MERCHANT_NAME, null)

    fun getMerchantEmail(): String? = preferences.getString(KEY_MERCHANT_EMAIL, null)

    fun getMerchantUpiId(): String? = preferences.getString(KEY_MERCHANT_UPI, null)

    fun getDeviceUuid(): String {
        var uuid = preferences.getString(KEY_DEVICE_UUID, null)
        if (uuid == null) {
            uuid = java.util.UUID.randomUUID().toString()
            preferences.edit().putString(KEY_DEVICE_UUID, uuid).apply()
        }
        return uuid
    }

    fun saveMerchantSession(merchant: MerchantInfo, accessToken: String, refreshToken: String) {
        preferences.edit()
            .putString(KEY_MERCHANT_ID, merchant.id)
            .putString(KEY_MERCHANT_NAME, merchant.name)
            .putString(KEY_MERCHANT_EMAIL, merchant.email)
            .putString(KEY_MERCHANT_UPI, merchant.upi_id)
            .putString(AuthInterceptor.KEY_MERCHANT_TOKEN, accessToken)
            .putString(KEY_REFRESH_TOKEN, refreshToken)
            .apply()
    }

    fun saveDeviceAuth(deviceId: String, deviceSecret: String, deviceToken: String) {
        preferences.edit()
            .putString(KEY_DEVICE_ID, deviceId)
            .putString(KEY_DEVICE_SECRET, deviceSecret)
            .putString(AuthInterceptor.KEY_DEVICE_TOKEN, deviceToken)
            .apply()
    }

    fun updateMerchantUpi(upiId: String) {
        preferences.edit().putString(KEY_MERCHANT_UPI, upiId).apply()
    }

    fun updateMerchantName(name: String) {
        preferences.edit().putString(KEY_MERCHANT_NAME, name).apply()
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val response = api.login(mapOf("email" to email, "password" to password))
            if (response.isSuccessful) {
                val data = response.body()!!.data
                saveMerchantSession(data.merchant, data.access_token, data.refresh_token)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(name: String, email: String, password: String, upiId: String?): Result<Unit> {
        return try {
            val body = mutableMapOf(
                "name" to name,
                "email" to email,
                "password" to password,
            )
            if (!upiId.isNullOrBlank()) {
                body["upi_id"] = upiId.trim()
            }
            val response = api.register(body)
            if (response.isSuccessful) {
                val data = response.body()!!.data
                saveMerchantSession(data.merchant, data.access_token, data.refresh_token)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Registration failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun needsUpiSetup(): Boolean = getMerchantUpiId().isNullOrBlank()

    fun isLoggedIn(): Boolean = getMerchantToken() != null && getDeviceId() != null

    fun logout() {
        preferences.edit().clear().apply()
    }

    companion object {
        private const val KEY_DEVICE_ID = "device_id"
        private const val KEY_DEVICE_SECRET = "device_secret"
        private const val KEY_MERCHANT_ID = "merchant_id"
        private const val KEY_MERCHANT_NAME = "merchant_name"
        private const val KEY_MERCHANT_EMAIL = "merchant_email"
        private const val KEY_MERCHANT_UPI = "merchant_upi"
        private const val KEY_DEVICE_UUID = "device_uuid"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }
}
