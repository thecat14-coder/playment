package com.gateway.android.ui.components

import java.text.NumberFormat
import java.util.Locale

fun formatPaise(paise: Int, currency: String = "INR"): String {
    val amount = paise / 100.0
    return if (currency == "INR") {
        NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(amount)
    } else {
        "$currency %.2f".format(amount)
    }
}

fun formatStatus(status: String): String =
    status.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
