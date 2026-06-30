package com.gateway.android.service

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.gateway.android.data.repo.AuthRepository
import com.gateway.android.data.repo.DeviceRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class HealthReporter @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val deviceRepo: DeviceRepository,
    private val authRepo: AuthRepository,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val deviceId = authRepo.getDeviceId() ?: return Result.failure()
        return try {
            val result = deviceRepo.sendHealthReport(deviceId)
            if (result.isSuccess) Result.success() else Result.retry()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        fun enqueue(context: Context) {
            val request = PeriodicWorkRequestBuilder<HealthReporter>(
                5, TimeUnit.MINUTES,
            )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build(),
                )
                .build()

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    "health_report",
                    ExistingPeriodicWorkPolicy.KEEP,
                    request,
                )
        }
    }
}
