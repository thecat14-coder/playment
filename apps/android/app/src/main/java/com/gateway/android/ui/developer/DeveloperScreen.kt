package com.gateway.android.ui.developer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeveloperScreen(navController: NavController, viewModel: DeveloperViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Developer") },
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
            Text("API keys", style = MaterialTheme.typography.titleMedium)
            state.apiKeys.forEach { key ->
                Text("${key.label ?: "Key"} · ${key.key_prefix}… · ${if (key.is_active) "Active" else "Inactive"}")
            }
            state.newApiKey?.let { key ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("New API key", style = MaterialTheme.typography.labelLarge)
                        Text(key, style = MaterialTheme.typography.bodySmall)
                        TextButton(onClick = { viewModel.copyKey(context, key) }) { Text("Copy key") }
                    }
                }
            }
            Button(onClick = { viewModel.regenerateApiKey() }, enabled = !state.isSaving) {
                Text("Generate API key")
            }

            HorizontalDivider()

            Text("Webhook", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = state.webhookUrl,
                onValueChange = { viewModel.updateWebhook(it) },
                label = { Text("Webhook URL (HTTPS)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { viewModel.saveWebhook() }, enabled = !state.isSaving) { Text("Save") }
                OutlinedButton(onClick = { viewModel.testWebhook() }) { Text("Test webhook") }
            }

            HorizontalDivider()

            Text("API documentation", style = MaterialTheme.typography.titleMedium)
            Text(
                "Base URL: your Railway backend\n" +
                    "Auth: Bearer <API_KEY> or merchant JWT\n" +
                    "Create links: POST /v1/payment-links\n" +
                    "List payments: GET /v1/payments",
                style = MaterialTheme.typography.bodyMedium,
            )

            state.message?.let { Text(it, color = MaterialTheme.colorScheme.secondary) }
            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        }
    }
}
