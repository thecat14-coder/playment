package com.gateway.android.ui.home

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gateway.android.service.ForegroundService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment Gateway") },
                actions = {
                    TextButton(onClick = { viewModel.logout(); navController.navigate("login") }) {
                        Text("Logout")
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = true, onClick = {}, icon = { Text("H") }, label = { Text("Home") })
                NavigationBarItem(selected = false, onClick = { navController.navigate("feed") }, icon = { Text("F") }, label = { Text("Feed") })
                NavigationBarItem(selected = false, onClick = { navController.navigate("diagnostics") }, icon = { Text("D") }, label = { Text("Diag") })
                NavigationBarItem(selected = false, onClick = { navController.navigate("settings") }, icon = { Text("S") }, label = { Text("Settings") })
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Device Status", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(24.dp))

            StatusIndicator("Online", state.isOnline)
            StatusIndicator("Notification Listener", state.listenerRunning)
            StatusIndicator("Health Score", state.healthScore > 70)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    context.startForegroundService(Intent(context, ForegroundService::class.java))
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Start Foreground Service")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { navController.navigate("feed") },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("View Payment Feed")
            }
        }
    }
}

@Composable
private fun StatusIndicator(label: String, isActive: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            modifier = Modifier.size(12.dp),
            shape = MaterialTheme.shapes.extraLarge,
            color = if (isActive) Color.Green else Color.Gray,
        ) {}
        Spacer(modifier = Modifier.width(12.dp))
        Text(label, style = MaterialTheme.typography.bodyLarge)
    }
}
