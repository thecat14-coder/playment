package com.gateway.android.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import com.gateway.android.data.repo.AuthRepository
import com.gateway.android.data.repo.DeviceRepository
import com.gateway.android.data.repo.EvidenceRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class ForegroundService : Service() {

    @Inject
    lateinit var deviceRepo: DeviceRepository

    @Inject
    lateinit var authRepo: AuthRepository

    @Inject
    lateinit var evidenceRepo: EvidenceRepository

    @Inject
    lateinit var onlineStateRepo: com.gateway.android.data.repo.OnlineStateRepository

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var isRunning = false

    override fun onCreate() {
        super.onCreate()
        isRunningFlag = true
        createNotificationChannel()

        val wakeLock = (getSystemService(POWER_SERVICE) as PowerManager)
            .newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Gateway:ForegroundLock")
        wakeLock.acquire(10 * 60 * 1000L)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = buildNotification()
        startForeground(NOTIFICATION_ID, notification)

        if (!isRunning) {
            isRunning = true
            startHeartbeatLoop()
            startHealthReportLoop()
            startEvidenceRetryLoop()
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        isRunning = false
        isRunningFlag = false
        scope.cancel()
        super.onDestroy()
    }

    private fun startHeartbeatLoop() {
        scope.launch {
            while (isRunning) {
                if (!onlineStateRepo.isMerchantOnline()) {
                    delay(HEARTBEAT_INTERVAL_MS)
                    continue
                }
                val deviceId = authRepo.getDeviceId()
                if (deviceId != null && authRepo.isLoggedIn()) {
                    deviceRepo.sendHeartbeat(deviceId, NotificationListenerService.isRunning)
                }
                delay(HEARTBEAT_INTERVAL_MS)
            }
        }
    }

    private fun startHealthReportLoop() {
        scope.launch {
            while (isRunning) {
                if (!onlineStateRepo.isMerchantOnline()) {
                    delay(HEALTH_REPORT_INTERVAL_MS)
                    continue
                }
                val deviceId = authRepo.getDeviceId()
                if (deviceId != null && authRepo.isLoggedIn()) {
                    deviceRepo.sendHealthReport(deviceId)
                }
                delay(HEALTH_REPORT_INTERVAL_MS)
            }
        }
    }

    private fun startEvidenceRetryLoop() {
        scope.launch {
            while (isRunning) {
                if (onlineStateRepo.isMerchantOnline()) {
                    evidenceRepo.retryFailed()
                }
                delay(RETRY_INTERVAL_MS)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Payment Gateway Service",
                NotificationManager.IMPORTANCE_LOW,
            ).apply {
                description = "Runs in background to detect UPI payments"
                setShowBadge(false)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Payment Gateway Active")
            .setContentText("Listening for UPI payment notifications")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    companion object {
        @Volatile
        var isRunningFlag: Boolean = false
        val isRunning: Boolean get() = isRunningFlag

        private const val CHANNEL_ID = "gateway_foreground_service"
        private const val NOTIFICATION_ID = 1
        private const val HEARTBEAT_INTERVAL_MS = 60_000L
        private const val HEALTH_REPORT_INTERVAL_MS = 300_000L
        private const val RETRY_INTERVAL_MS = 120_000L
    }
}
