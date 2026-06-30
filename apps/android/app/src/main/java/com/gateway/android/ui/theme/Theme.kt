package com.gateway.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF1A73E8),
    secondary = androidx.compose.ui.graphics.Color(0xFF34A853),
    error = androidx.compose.ui.graphics.Color(0xFFEA4335),
)

@Composable
fun GatewayTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = LightColorScheme, content = content)
}
