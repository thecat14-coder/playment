package com.gateway.android.data.repo

import com.gateway.android.data.api.GatewayApi
import com.gateway.android.data.db.EvidenceDao
import com.gateway.android.data.db.PendingEvidence
import com.gateway.android.domain.parser.NotificationParser
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EvidenceRepository @Inject constructor(
    private val api: GatewayApi,
    private val evidenceDao: EvidenceDao,
    private val parser: NotificationParser,
    private val authRepo: AuthRepository,
) {
    fun getAllPending(): Flow<List<PendingEvidence>> = evidenceDao.getAll()

    suspend fun processAndUpload(
        notificationTitle: String?,
        notificationBody: String,
        packageName: String,
        timestamp: Long,
    ) {
        val parsed = parser.parse(notificationTitle, notificationBody, packageName)
        if (parsed == null) return

        val nonce = UUID.randomUUID().toString()
        val deviceSecret = authRepo.getDeviceSecret() ?: return

        val signature = computeSignature(parsed, nonce, deviceSecret, timestamp)

        val evidence = PendingEvidence(
            id = nonce,
            rawNotification = notificationBody,
            amount = parsed.amount,
            utr = parsed.utr,
            rrn = null,
            senderVpa = parsed.senderVpa,
            senderName = parsed.senderName,
            upiApp = parsed.upiApp,
            bank = parsed.bank,
            notificationPackage = packageName,
            notificationTimestamp = timestamp,
            parserVersion = "1.0.0",
            nonce = nonce,
            signature = signature,
        )

        evidenceDao.insert(evidence)
        uploadEvidence(evidence)
    }

    suspend fun uploadEvidence(evidence: PendingEvidence) {
        try {
            val body = mutableMapOf<String, Any>(
                "raw_notification" to evidence.rawNotification,
                "amount" to evidence.amount,
                "utr" to (evidence.utr ?: ""),
                "upi_app" to evidence.upiApp,
                "bank" to (evidence.bank ?: ""),
                "notification_package" to evidence.notificationPackage,
                "notification_timestamp" to evidence.notificationTimestamp,
                "parser_version" to evidence.parserVersion,
                "nonce" to evidence.nonce,
                "signature" to evidence.signature,
            )
            if (!evidence.senderVpa.isNullOrBlank()) {
                body["sender_vpa"] = evidence.senderVpa
            }
            if (!evidence.senderName.isNullOrBlank()) {
                body["sender_name"] = evidence.senderName
            }
            val response = api.uploadEvidence(body)
            if (response.isSuccessful) {
                evidenceDao.deleteById(evidence.id)
            } else {
                android.util.Log.e("EvidenceRepo", "Upload failed: ${response.code()} ${response.errorBody()?.string()}")
                evidenceDao.incrementRetry(evidence.id)
            }
        } catch (e: Exception) {
            android.util.Log.e("EvidenceRepo", "Upload error: ${e.message}")
            evidenceDao.incrementRetry(evidence.id)
        }
    }

    suspend fun retryFailed() {
        val pending = evidenceDao.getPendingRetries()
        for (evidence in pending) {
            uploadEvidence(evidence)
        }
    }

    private fun computeSignature(
        parsed: NotificationParser.ParsedNotification,
        nonce: String,
        deviceSecret: String,
        timestamp: Long,
    ): String {
        val data = "${parsed.amount}|${parsed.utr ?: ""}|$timestamp|${parsed.rawText.take(100)}"
        val hmac = javax.crypto.Mac.getInstance("HmacSHA256")
        val key = javax.crypto.spec.SecretKeySpec(deviceSecret.toByteArray(), "HmacSHA256")
        hmac.init(key)
        return hmac.doFinal(data.toByteArray()).joinToString("") { "%02x".format(it) }
    }
}
