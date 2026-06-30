package com.gateway.android.domain.usecase

import com.gateway.android.data.repo.AuthRepository
import com.gateway.android.data.repo.DeviceRepository
import com.gateway.android.service.NotificationListenerService
import javax.inject.Inject

class SendHeartbeatUseCase @Inject constructor(
    private val deviceRepo: DeviceRepository,
    private val authRepo: AuthRepository,
) {
    suspend operator fun invoke(): Result<Unit> {
        val deviceId = authRepo.getDeviceId() ?: return Result.failure(Exception("Not registered"))
        val listenerRunning = NotificationListenerService.isRunning
        return deviceRepo.sendHeartbeat(deviceId, listenerRunning)
    }
}
