package com.gateway.android.data.api

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val preferences: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val token = preferences.getString(KEY_DEVICE_TOKEN, null)
        if (token != null && original.header("Authorization") == null) {
            val request = original.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            return chain.proceed(request)
        }

        return chain.proceed(original)
    }

    companion object {
        const val KEY_DEVICE_TOKEN = "device_token"
    }
}
