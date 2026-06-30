package com.gateway.android.ui.health

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gateway.android.data.repo.DeviceRepository
import com.gateway.android.data.repo.MerchantRepository
import com.gateway.android.data.repo.OnlineStateRepository
import com.gateway.android.domain.model.HealthStatus
import com.gateway.android.service.ForegroundService
import com.gateway.android.service.NotificationListenerService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DeviceHealthUiState(
    val health: HealthStatus? = null,
    val healthScore: Int = 0,
    val isOnline: Boolean = false,
    val isLoading: Boolean = true,
)

@HiltViewModel
class DeviceHealthViewModel @Inject constructor(
    private val deviceRepo: DeviceRepository,
    private val merchantRepo: MerchantRepository,
    private val onlineStateRepo: OnlineStateRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(DeviceHealthUiState())
    val state = _state.asStateFlow()

    init { refresh() }

    fun refresh() {
        viewModelScope.launch {
            _state.value = DeviceHealthUiState(
                health = deviceRepo.getDeviceHealth(),
                healthScore = merchantRepo.getHealthSummary().getOrNull()?.health_score ?: 0,
                isOnline = onlineStateRepo.isMerchantOnline(),
                isLoading = false,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceHealthScreen(navController: NavController, viewModel: DeviceHealthViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val health = state.health

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Device health") },
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) { Text("Back") }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Health score: ${state.healthScore}/100", style = MaterialTheme.typography.headlineSmall)
            Text(if (state.isOnline) "Merchant is ONLINE" else "Merchant is OFFLINE")

            health?.let {
                HealthRow("Notification permission", it.notificationPermission) {
                    context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                }
                HealthRow("Battery optimization off", it.batteryOptimizationDisabled) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        context.startActivity(
                            Intent(
                                Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                                android.net.Uri.parse("package:${context.packageName}"),
                            ),
                        )
                    }
                }
                HealthRow("Foreground service", it.foregroundServiceRunning, action = null)
                HealthRow("Listener running", it.listenerRunning, action = null)
                HealthRow("Internet", it.internetConnected, action = null)
                Text("Battery: ${it.batteryLevel}%")
            }

            Button(onClick = { viewModel.refresh() }) { Text("Refresh") }
        }
    }
}

@Composable
private fun HealthRow(label: String, ok: Boolean, action: (() -> Unit)?) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text("${if (ok) "✓" else "✗"} $label")
        if (!ok && action != null) {
            TextButton(onClick = action) { Text("Fix") }
        }
    }
}
