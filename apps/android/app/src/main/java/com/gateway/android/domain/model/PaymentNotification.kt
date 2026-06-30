package com.gateway.android.domain.model

data class PaymentNotification(
    val id: String,
    val rawText: String,
    val amount: Int,
    val utr: String?,
    val senderVpa: String?,
    val senderName: String?,
    val upiApp: String,
    val bank: String?,
    val timestamp: Long,
    val matched: Boolean = false,
)
