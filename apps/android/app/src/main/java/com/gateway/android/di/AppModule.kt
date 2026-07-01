package com.gateway.android.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.gateway.android.BuildConfig
import com.gateway.android.data.api.GatewayApi
import com.gateway.android.data.api.AuthInterceptor
import com.gateway.android.data.api.DeviceTokenAuthenticator
import com.gateway.android.data.db.AppDatabase
import com.gateway.android.data.db.EvidenceDao
import com.gateway.android.data.repo.AuthRepository
import com.gateway.android.data.repo.DeviceRepository
import com.gateway.android.data.repo.EvidenceRepository
import com.gateway.android.domain.parser.NotificationParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            "gateway_secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(preferences: SharedPreferences): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(preferences))
            .authenticator(DeviceTokenAuthenticator(preferences))
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideGatewayApi(client: OkHttpClient): GatewayApi {
        val baseUrl = BuildConfig.API_BASE_URL.trimEnd('/') + "/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GatewayApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.create(context)
    }

    @Provides
    @Singleton
    fun provideEvidenceDao(db: AppDatabase): EvidenceDao {
        return db.evidenceDao()
    }

    @Provides
    @Singleton
    fun provideNotificationParser(): NotificationParser {
        return NotificationParser()
    }
}
