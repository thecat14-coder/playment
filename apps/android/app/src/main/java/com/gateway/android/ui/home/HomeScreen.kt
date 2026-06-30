package com.gateway.android.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gateway.android.ui.components.MerchantBottomBar
import com.gateway.android.ui.components.formatPaise
import com.gateway.android.ui.components.formatStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Hello, ${state.merchantName}")
                        Text(
                            if (state.isOnline) "You are online" else "You are offline",
                            style = MaterialTheme.typography.labelMedium,
                            color = if (state.isOnline) Color(0xFF00A86B) else MaterialTheme.colorScheme.error,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("health") }) {
                        Icon(Icons.Default.HealthAndSafety, contentDescription = "Device health")
                    }
                },
            )
        },
        bottomBar = { MerchantBottomBar(navController, "home") },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("links/create") }) {
                Icon(Icons.Default.Add, contentDescription = "Create link")
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
                OnlineToggleCard(
                    isOnline = state.isOnline,
                    isLoading = state.isTogglingOnline,
                    onToggle = { viewModel.toggleOnline() },
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard(
                        title = "Today's collection",
                        value = formatPaise(state.todayCollectionPaise),
                        modifier = Modifier.weight(1f),
                    )
                    StatCard(
                        title = "Payments today",
                        value = state.todayPaymentCount.toString(),
                        modifier = Modifier.weight(1f),
                    )
                }

                DeviceStatusCard(
                    listenerRunning = state.listenerRunning,
                    healthScore = state.healthScore,
                )

                state.lastPayment?.let { payment ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Last payment", style = MaterialTheme.typography.labelLarge)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(formatPaise(payment.amount, payment.currency), style = MaterialTheme.typography.titleLarge)
                            Text(
                                "${formatStatus(payment.status)} · ${payment.order_id}",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }

                state.error?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = { navController.navigate("links") },
                        modifier = Modifier.weight(1f),
                    ) { Text("Payment links") }
                    OutlinedButton(
                        onClick = { navController.navigate("payments") },
                        modifier = Modifier.weight(1f),
                    ) { Text("All payments") }
                }
        }
    }
}

@Composable
private fun OnlineToggleCard(isOnline: Boolean, isLoading: Boolean, onToggle: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isOnline) Color(0xFFE8F8F0) else MaterialTheme.colorScheme.surfaceVariant,
        ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text("Go online", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(
                    if (isOnline) "Listening for UPI payments" else "Tap to start accepting payments",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(32.dp))
            } else {
                Switch(checked = isOnline, onCheckedChange = { onToggle() })
            }
        }
    }
}

@Composable
private fun StatCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun DeviceStatusCard(listenerRunning: Boolean, healthScore: Int) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Device status", style = MaterialTheme.typography.titleMedium)
            StatusRow("Notification listener", listenerRunning)
            StatusRow("Health score", healthScore >= 70, "${healthScore}/100")
        }
    }
}

@Composable
private fun StatusRow(label: String, ok: Boolean, detail: String? = null) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            modifier = Modifier.size(10.dp),
            shape = MaterialTheme.shapes.extraLarge,
            color = if (ok) Color(0xFF00A86B) else Color.Gray,
        ) {}
        Spacer(modifier = Modifier.width(10.dp))
        Text(label, modifier = Modifier.weight(1f))
        Text(detail ?: if (ok) "OK" else "Issue", style = MaterialTheme.typography.bodySmall)
    }
}
