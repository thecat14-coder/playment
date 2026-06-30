package com.gateway.android.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_evidence")
data class PendingEvidence(
    @PrimaryKey val id: String,
    val rawNotification: String,
    val amount: Int,
    val utr: String?,
    val rrn: String?,
    val senderVpa: String?,
    val senderName: String?,
    val upiApp: String,
    val bank: String?,
    val notificationPackage: String,
    val notificationTimestamp: Long,
    val parserVersion: String,
    val nonce: String,
    val signature: String,
    val retryCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
)
