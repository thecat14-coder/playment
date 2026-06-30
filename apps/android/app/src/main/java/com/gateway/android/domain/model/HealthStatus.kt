package com.gateway.android.domain.model

data class HealthStatus(
    val notificationPermission: Boolean,
    val batteryOptimizationDisabled: Boolean,
    val foregroundServiceRunning: Boolean,
    val listenerRunning: Boolean,
    val internetConnected: Boolean,
    val batteryLevel: Int,
)
