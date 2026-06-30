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

    fun parse(title: String?, body: String, packageName: String): ParsedNotification? {
        val fullText = "$title $body"
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
            Pattern.compile("(?:Rs\\.?|INR|₹)\\s?(\\d{1,10}(?:,\\d{3})*(?:\\.\\d{2})?)"),
            Pattern.compile("credited by\\s?(?:Rs\\.?|INR|₹)?\\s?(\\d+)"),
            Pattern.compile("amount\\s?(?:of\\s?)?(?:Rs\\.?|INR|₹)?\\s?(\\d+)"),
            Pattern.compile("(\\d+)\\s?(?:rupees|rs)"),
        )

        for (pattern in patterns) {
            val matcher = pattern.matcher(text)
            if (matcher.find()) {
                val raw = matcher.group(1)!!.replace(",", "")
                val amountFloat = raw.toDoubleOrNull() ?: continue
                return (amountFloat * 100).toInt()
            }
        }
        return null
    }

    private fun extractUtr(text: String): String? {
        val patterns = listOf(
            Pattern.compile("UTR(?:\\s?(?:No|Number|#|:))?\\s?[:\\.]?\\s?(\\d{12})"),
            Pattern.compile("(\\d{12})(?:\\s?(?:is|has been))"),
            Pattern.compile("(\\d{12})"),
        )

        for (pattern in patterns) {
            val matcher = pattern.matcher(text)
            if (matcher.find()) {
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
            Pattern.compile("from\\s+([A-Za-z\\s]+?)(?:\\s+(?:has|sent|paid|transferred|credited))"),
            Pattern.compile("received from\\s+([A-Za-z\\s]+?)(?:\\s+(?:has|sent|via|using))"),
            Pattern.compile("by\\s+([A-Za-z\\s]+?)(?:\\s+(?:has|sent|to|for))"),
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
            else -> packageName
        }
    }
}
