package com.gateway.android.data.repo

import android.content.SharedPreferences
import com.gateway.android.data.api.GatewayApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: GatewayApi,
    private val preferences: SharedPreferences,
) {
    fun getDeviceToken(): String? = preferences.getString(KEY_DEVICE_TOKEN, null)

    fun getDeviceId(): String? = preferences.getString(KEY_DEVICE_ID, null)

    fun getDeviceSecret(): String? = preferences.getString(KEY_DEVICE_SECRET, null)

    fun getMerchantId(): String? = preferences.getString(KEY_MERCHANT_ID, null)

    fun getDeviceUuid(): String {
        var uuid = preferences.getString(KEY_DEVICE_UUID, null)
        if (uuid == null) {
            uuid = java.util.UUID.randomUUID().toString()
            preferences.edit().putString(KEY_DEVICE_UUID, uuid).apply()
        }
        return uuid
    }

    fun saveDeviceAuth(deviceId: String, deviceSecret: String, merchantId: String, token: String) {
        preferences.edit()
            .putString(KEY_DEVICE_ID, deviceId)
            .putString(KEY_DEVICE_SECRET, deviceSecret)
            .putString(KEY_MERCHANT_ID, merchantId)
            .putString(KEY_DEVICE_TOKEN, token)
            .apply()
    }

    fun saveToken(token: String) {
        preferences.edit().putString(KEY_DEVICE_TOKEN, token).apply()
    }

    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val response = api.login(mapOf("email" to email, "password" to password))
            if (response.isSuccessful) {
                val data = response.body()!!.data
                preferences.edit()
                    .putString(KEY_MERCHANT_ID, data.merchant.id)
                    .putString(KEY_DEVICE_TOKEN, data.access_token)
                    .apply()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Login failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun isLoggedIn(): Boolean = getDeviceToken() != null && getDeviceId() != null

    fun logout() {
        preferences.edit().clear().apply()
    }

    companion object {
        private const val KEY_DEVICE_TOKEN = "device_token"
        private const val KEY_DEVICE_ID = "device_id"
        private const val KEY_DEVICE_SECRET = "device_secret"
        private const val KEY_MERCHANT_ID = "merchant_id"
        private const val KEY_DEVICE_UUID = "device_uuid"
    }
}
