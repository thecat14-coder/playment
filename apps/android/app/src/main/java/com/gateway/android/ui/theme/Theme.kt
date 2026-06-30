package com.gateway.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FintechLight = lightColorScheme(
    primary = Color(0xFF0F4C81),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6E8FF),
    secondary = Color(0xFF00A86B),
    onSecondary = Color.White,
    tertiary = Color(0xFF7C4DFF),
    background = Color(0xFFF4F7FB),
    surface = Color.White,
    onSurface = Color(0xFF1A1C1E),
    error = Color(0xFFD32F2F),
)

@Composable
fun GatewayTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = FintechLight,
        content = content,
    )
}
