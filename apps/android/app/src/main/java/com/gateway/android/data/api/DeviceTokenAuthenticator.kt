package com.gateway.android.data.api

import android.content.SharedPreferences
import com.gateway.android.BuildConfig
import okhttp3.Authenticator
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.Route
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class DeviceTokenAuthenticator(
    private val preferences: SharedPreferences,
) : Authenticator {

    private val refreshClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val baseUrl = BuildConfig.API_BASE_URL.trimEnd('/') + "/"
    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code != 401) return null
        if (response.request.header(RETRY_HEADER) == "true") return null

        val path = response.request.url.encodedPath
        if (!AuthInterceptor.isDeviceAuthPath(path)) return null

        synchronized(this) {
            val requestToken = response.request.header("Authorization")?.removePrefix("Bearer ")?.trim()
            val storedToken = preferences.getString(AuthInterceptor.KEY_DEVICE_TOKEN, null)
            if (storedToken != null && storedToken != requestToken) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $storedToken")
                    .build()
            }

            val newToken = refreshDeviceToken() ?: return null
            preferences.edit()
                .putString(AuthInterceptor.KEY_DEVICE_TOKEN, newToken)
                .apply()

            return response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .header(RETRY_HEADER, "true")
                .build()
        }
    }

    private fun refreshDeviceToken(): String? {
        refreshViaDeviceRefreshToken()?.let { return it }
        return refreshViaDeviceRegister()
    }

    private fun refreshViaDeviceRefreshToken(): String? {
        val refreshToken = preferences.getString(KEY_DEVICE_REFRESH_TOKEN, null) ?: return null
        val body = JSONObject().put("refresh_token", refreshToken).toString()
        val request = Request.Builder()
            .url("${baseUrl}v1/devices/refresh")
            .post(body.toRequestBody(jsonMediaType))
            .build()

        refreshClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null
            val json = JSONObject(response.body?.string() ?: return null)
            val data = json.optJSONObject("data") ?: return null
            val token = data.optString("token", "")
            val newRefresh = data.optString("refresh_token", "")
            if (token.isBlank()) return null
            if (newRefresh.isNotBlank()) {
                preferences.edit().putString(KEY_DEVICE_REFRESH_TOKEN, newRefresh).apply()
            }
            return token
        }
    }

    private fun refreshViaDeviceRegister(): String? {
        val merchantToken = preferences.getString(AuthInterceptor.KEY_MERCHANT_TOKEN, null) ?: return null
        val deviceUuid = preferences.getString(KEY_DEVICE_UUID, null)
            ?: java.util.UUID.randomUUID().toString().also {
                preferences.edit().putString(KEY_DEVICE_UUID, it).apply()
            }

        val body = JSONObject()
            .put("device_uuid", deviceUuid)
            .put("model", android.os.Build.MODEL)
            .put("manufacturer", android.os.Build.MANUFACTURER)
            .put("android_version", android.os.Build.VERSION.RELEASE)
            .put("app_version", "1.0.0")
            .toString()

        val request = Request.Builder()
            .url("${baseUrl}v1/devices/register")
            .post(body.toRequestBody(jsonMediaType))
            .header("Authorization", "Bearer $merchantToken")
            .build()

        refreshClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return null
            val json = JSONObject(response.body?.string() ?: return null)
            val data = json.optJSONObject("data") ?: return null
            val token = data.optString("token", "")
            val deviceId = data.optString("device_id", "")
            val deviceSecret = data.optString("device_secret", "")
            val refreshToken = data.optString("refresh_token", "")
            if (token.isBlank() || deviceId.isBlank()) return null

            preferences.edit()
                .putString(AuthInterceptor.KEY_DEVICE_TOKEN, token)
                .putString(KEY_DEVICE_ID, deviceId)
                .putString(KEY_DEVICE_SECRET, deviceSecret)
                .apply()
            if (refreshToken.isNotBlank()) {
                preferences.edit().putString(KEY_DEVICE_REFRESH_TOKEN, refreshToken).apply()
            }
            return token
        }
    }

    companion object {
        private const val RETRY_HEADER = "X-Device-Auth-Retry"
        const val KEY_DEVICE_REFRESH_TOKEN = "device_refresh_token"
        const val KEY_DEVICE_ID = "device_id"
        const val KEY_DEVICE_SECRET = "device_secret"
        const val KEY_DEVICE_UUID = "device_uuid"
    }
}
