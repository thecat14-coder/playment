package com.gateway.android.data.repo

import android.content.SharedPreferences
import com.gateway.android.data.api.GatewayApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnlineStateRepository @Inject constructor(
    private val api: GatewayApi,
    private val authRepo: AuthRepository,
    private val deviceRepo: DeviceRepository,
    private val preferences: SharedPreferences,
) {
    private val _isOnline = MutableStateFlow(preferences.getBoolean(KEY_IS_ONLINE, false))
    val isOnline = _isOnline.asStateFlow()

    fun isMerchantOnline(): Boolean = _isOnline.value

    suspend fun setOnline(online: Boolean): Result<Unit> {
        val deviceId = authRepo.getDeviceId()
            ?: return Result.failure(Exception("Device not registered"))

        return try {
            if (authRepo.getDeviceToken() == null) {
                val refresh = deviceRepo.registerDevice()
                if (refresh.isFailure) {
                    return Result.failure(refresh.exceptionOrNull() ?: Exception("Device token refresh failed"))
                }
            }

            val response = api.updateDeviceStatus(deviceId, mapOf("is_online" to online))
            if (response.isSuccessful) {
                preferences.edit().putBoolean(KEY_IS_ONLINE, online).apply()
                _isOnline.value = online
                Result.success(Unit)
            } else {
                Result.failure(Exception("Status update failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun clear() {
        preferences.edit().putBoolean(KEY_IS_ONLINE, false).apply()
        _isOnline.value = false
    }

    companion object {
        private const val KEY_IS_ONLINE = "merchant_is_online"
    }
}
