package com.gateway.android.ui.diagnostics

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gateway.android.service.NotificationListenerService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagnosticsScreen(navController: NavController? = null) {
    val context = LocalContext.current

    val notificationAccess = Settings.Secure.getString(
        context.contentResolver,
        "enabled_notification_listeners",
    )?.contains(context.packageName) == true

    val batteryOptimizationDisabled = (context.getSystemService(Context.POWER_SERVICE) as PowerManager)
        .isIgnoringBatteryOptimizations(context.packageName)

    val listenerRunning = NotificationListenerService.isRunning

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Diagnostics") },
                navigationIcon = {
                    if (navController != null) {
                        TextButton(onClick = { navController.popBackStack() }) {
                            Text("Back")
                        }
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
        ) {
            DiagnosticItem("Notification Access", notificationAccess)
            Spacer(modifier = Modifier.height(12.dp))
            DiagnosticItem("Battery Optimization Off", batteryOptimizationDisabled)
            Spacer(modifier = Modifier.height(12.dp))
            DiagnosticItem("Listener Service Running", listenerRunning)
            Spacer(modifier = Modifier.height(24.dp))

            if (!notificationAccess) {
                Button(onClick = {
                    context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                }) {
                    Text("Enable Notification Access")
                }
            }

            if (!batteryOptimizationDisabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = {
                    val intent = Intent(
                        Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                        android.net.Uri.parse("package:${context.packageName}"),
                    )
                    context.startActivity(intent)
                }) {
                    Text("Disable Battery Optimization")
                }
            }
        }
    }
}

@Composable
private fun DiagnosticItem(label: String, isOk: Boolean) {
    Row {
        Text(
            if (isOk) "\u2705" else "\u274C",
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, style = MaterialTheme.typography.bodyLarge)
    }
}
