package com.gateway.android.data.repo

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import com.gateway.android.data.api.GatewayApi
import com.gateway.android.domain.model.HealthStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepository @Inject constructor(
    private val api: GatewayApi,
    private val authRepo: AuthRepository,
    @ApplicationContext private val context: Context,
) {
    suspend fun registerDevice(): Result<String> {
        return try {
            val body = mapOf<String, Any>(
                "device_uuid" to authRepo.getDeviceUuid(),
                "model" to Build.MODEL,
                "manufacturer" to Build.MANUFACTURER,
                "android_version" to Build.VERSION.RELEASE,
                "app_version" to getAppVersion(),
            )
            val response = api.registerDevice(body)
            if (response.isSuccessful) {
                val data = response.body()!!.data
                authRepo.saveDeviceAuth(
                    deviceId = data.device_id,
                    deviceSecret = data.device_secret,
                    merchantId = authRepo.getMerchantId() ?: "",
                    token = data.token,
                )
                Result.success(data.device_id)
            } else {
                Result.failure(Exception("Registration failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendHeartbeat(deviceId: String, listenerRunning: Boolean): Result<Unit> {
        return try {
            val response = api.sendHeartbeat(deviceId, mapOf("listener_running" to listenerRunning))
            if (response.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Heartbeat failed: ${response.code()}"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendHealthReport(deviceId: String): Result<Int> {
        val health = getDeviceHealth()
        return try {
            val response = api.sendHealthReport(deviceId, mapOf(
                "notification_permission" to health.notificationPermission,
                "battery_optimization_disabled" to health.batteryOptimizationDisabled,
                "foreground_service_running" to health.foregroundServiceRunning,
                "listener_running" to health.listenerRunning,
                "internet_connected" to health.internetConnected,
                "battery_level" to health.batteryLevel,
                "app_version" to getAppVersion(),
            ))
            if (response.isSuccessful) {
                val score = response.body()?.get("data") as? Map<*, *>
                val healthScore = (score?.get("health_score") as? Double)?.toInt() ?: 0
                Result.success(healthScore)
            } else {
                Result.failure(Exception("Health report failed: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getDeviceHealth(): HealthStatus {
        val pm = context.packageManager
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager

        return HealthStatus(
            notificationPermission = Settings.Secure.getString(
                context.contentResolver,
                "enabled_notification_listeners",
            )?.contains(context.packageName) == true,
            batteryOptimizationDisabled = powerManager.isIgnoringBatteryOptimizations(context.packageName),
            foregroundServiceRunning = false,
            listenerRunning = false,
            internetConnected = isNetworkConnected(),
            batteryLevel = getBatteryLevel(),
        )
    }

    private fun getAppVersion(): String {
        return try {
            val pkgInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pkgInfo.versionName ?: "1.0.0"
        } catch (e: PackageManager.NameNotFoundException) {
            "1.0.0"
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        return cm.activeNetwork != null
    }

    private fun getBatteryLevel(): Int {
        val intent = context.registerReceiver(null, android.content.IntentFilter(android.content.Intent.ACTION_BATTERY_CHANGED))
        val level = intent?.getIntExtra(android.os.BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = intent?.getIntExtra(android.os.BatteryManager.EXTRA_SCALE, -1) ?: -1
        return if (level >= 0 && scale > 0) (level * 100) / scale else 50
    }
}
