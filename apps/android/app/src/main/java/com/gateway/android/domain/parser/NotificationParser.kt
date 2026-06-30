package com.gateway.android.domain.parser

import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationParser @Inject constructor() {

    data class ParsedNotification(
        val rawText: String,
        val amount: Int,
        val utr: String?,
        val senderVpa: String?,
        val senderName: String?,
        val upiApp: String,
        val bank: String?,
    )

    fun isLikelyPaymentNotification(packageName: String, title: String?, body: String): Boolean {
        if (PAYMENT_PACKAGES.contains(packageName)) return true
        val text = "$title $body".lowercase()
        return text.contains("credited") ||
            text.contains("received") ||
            text.contains("debited") ||
            text.contains("upi") ||
            text.contains("paid") ||
            text.contains("₹") ||
            text.contains("rs.") ||
            text.contains("inr")
    }

    fun parse(title: String?, body: String, packageName: String): ParsedNotification? {
        val fullText = "${title.orEmpty()} $body"
        val amount = extractAmount(fullText) ?: return null

        val upiApp = resolveAppName(packageName)
        val utr = extractUtr(fullText)
        val senderVpa = extractVpa(fullText)
        val senderName = extractSenderName(fullText)
        val bank = extractBank(fullText)

        return ParsedNotification(
            rawText = body,
            amount = amount,
            utr = utr,
            senderVpa = senderVpa,
            senderName = senderName,
            upiApp = upiApp,
            bank = bank,
        )
    }

    private fun extractAmount(text: String): Int? {
        val patterns = listOf(
            Pattern.compile("(?:Rs\\.?|INR|₹)\\s?(\\d{1,10}(?:,\\d{3})*(?:\\.\\d{1,2})?)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(?:credited|received|paid)\\s+(?:with\\s+)?(?:Rs\\.?|INR|₹)?\\s?(\\d+(?:\\.\\d{1,2})?)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(?:amount|amt)\\s*(?:of\\s+)?(?:Rs\\.?|INR|₹)?\\s?(\\d+(?:\\.\\d{1,2})?)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(\\d+(?:\\.\\d{1,2})?)\\s?(?:rupees|rs\\.?)", Pattern.CASE_INSENSITIVE),
        )

        for (pattern in patterns) {
            val matcher = pattern.matcher(text)
            if (matcher.find()) {
                val raw = matcher.group(1)!!.replace(",", "")
                val amountFloat = raw.toDoubleOrNull() ?: continue
                if (amountFloat <= 0) continue
                return (amountFloat * 100).toInt()
            }
        }
        return null
    }

    private fun extractUtr(text: String): String? {
        val patterns = listOf(
            Pattern.compile("(?:UTR|UPI\\s*Ref(?:erence)?|Ref(?:erence)?\\s*(?:No|Number|#)?)\\s*[:.]?\\s*(\\d{12})", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\b(\\d{12})\\b"),
        )

        for (pattern in patterns) {
            val matcher = pattern.matcher(text)
            while (matcher.find()) {
                val candidate = matcher.group(1)
                if (candidate?.length == 12 && candidate.all { it.isDigit() }) {
                    return candidate
                }
            }
        }
        return null
    }

    private fun extractVpa(text: String): String? {
        val pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9]+")
        val matcher = pattern.matcher(text)
        return if (matcher.find()) matcher.group() else null
    }

    private fun extractSenderName(text: String): String? {
        val patterns = listOf(
            Pattern.compile("(?:from|by)\\s+([A-Za-z][A-Za-z0-9\\s.'-]{1,40}?)(?:\\s+(?:on|via|using|has|sent|for|to|\\d))", Pattern.CASE_INSENSITIVE),
            Pattern.compile("received from\\s+([A-Za-z][A-Za-z0-9\\s.'-]{1,40}?)(?:\\s+(?:on|via|using))", Pattern.CASE_INSENSITIVE),
        )

        for (pattern in patterns) {
            val matcher = pattern.matcher(text)
            if (matcher.find()) {
                return matcher.group(1)?.trim()?.take(50)
            }
        }
        return null
    }

    private fun extractBank(text: String): String? {
        val bankPatterns = listOf(
            "HDFC Bank", "ICICI Bank", "SBI", "State Bank of India",
            "Axis Bank", "Kotak", "Yes Bank", "PNB", "Bank of Baroda",
            "Canara Bank", "Union Bank", "IDFC Bank", "IndusInd Bank",
            "Federal Bank", "Bandhan Bank",
        )

        for (bank in bankPatterns) {
            if (text.contains(bank, ignoreCase = true)) return bank
        }
        return null
    }

    private fun resolveAppName(packageName: String): String {
        return when (packageName) {
            "com.phonepe.app" -> "PhonePe"
            "com.google.android.apps.nbu.paisa.user" -> "Google Pay"
            "net.one97.paytm" -> "Paytm"
            "in.org.npci.upiapp" -> "BHIM"
            "com.dreamplug.androidapp" -> "CRED"
            "in.amazon.mShop.android.shopping" -> "Amazon Pay"
            "com.whatsapp" -> "WhatsApp"
            "com.axis.mobile" -> "Axis Bank"
            "com.hdfcbank.hdfcpay" -> "HDFC Bank"
            "com.csam.icici.bank.imobile" -> "ICICI Bank"
            "com.sbi.lotusintouch" -> "SBI"
            else -> packageName
        }
    }

    companion object {
        private val PAYMENT_PACKAGES = setOf(
            "com.phonepe.app",
            "com.google.android.apps.nbu.paisa.user",
            "net.one97.paytm",
            "in.org.npci.upiapp",
            "com.dreamplug.androidapp",
            "in.amazon.mShop.android.shopping",
            "com.whatsapp",
            "com.axis.mobile",
            "com.hdfcbank.hdfcpay",
            "com.csam.icici.bank.imobile",
            "com.sbi.lotusintouch",
            "com.google.android.apps.messaging",
        )
    }
}
