package com.gateway.android.data.api

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val preferences: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        if (original.header("Authorization") != null) {
            return chain.proceed(original)
        }

        val path = original.url.encodedPath
        if (path.contains("/v1/auth/")) {
            return chain.proceed(original)
        }

        val token = selectToken(path)
        if (token == null) {
            return chain.proceed(original)
        }

        val request = original.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }

    private fun selectToken(path: String): String? {
        val deviceToken = preferences.getString(KEY_DEVICE_TOKEN, null)
        val merchantToken = preferences.getString(KEY_MERCHANT_TOKEN, null)

        val deviceOnly = path.contains("/v1/evidence") || isDeviceAuthPath(path)

        return if (deviceOnly) deviceToken else merchantToken
    }

    companion object {
        const val KEY_DEVICE_TOKEN = "device_token"
        const val KEY_MERCHANT_TOKEN = "merchant_token"

        fun isDeviceAuthPath(path: String): Boolean =
            DEVICE_AUTH_PATTERN.containsMatchIn(path)

        private val DEVICE_AUTH_PATTERN = Regex(
            """/v1/devices/[^/]+/(heartbeat|health|status)(/|$)""",
        )
    }
}
