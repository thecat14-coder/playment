package com.gateway.android.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.gateway.android.data.repo.EvidenceRepository
import com.gateway.android.domain.parser.NotificationParser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationListenerService : NotificationListenerService() {

    @Inject
    lateinit var evidenceRepo: EvidenceRepository

    @Inject
    lateinit var parser: NotificationParser

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)

        val packageName = sbn.packageName
        val notification = sbn.notification
        val extras = notification.extras

        val title = extras.getString(EXTRA_TITLE)
        val body = extras.getCharSequence(EXTRA_TEXT)?.toString()
            ?: extras.getCharSequence(EXTRA_BIG_TEXT)?.toString()
            ?: return

        val timestamp = sbn.postTime

        Log.d(TAG, "Notification from $packageName: $title")

        if (!parser.isLikelyPaymentNotification(packageName, title, body)) {
            return
        }

        scope.launch {
            evidenceRepo.processAndUpload(title, body, packageName, timestamp)
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {}

    companion object {
        private const val TAG = "NotifListener"
        private const val EXTRA_TITLE = "android.title"
        private const val EXTRA_TEXT = "android.text"
        private const val EXTRA_BIG_TEXT = "android.bigText"

        @Volatile
        var isRunning = false
            private set
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        isRunning = true
        Log.d(TAG, "Notification listener connected")
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        isRunning = false
        Log.d(TAG, "Notification listener disconnected")
    }
}
