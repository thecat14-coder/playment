package com.gateway.android.ui.links

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gateway.android.ui.components.formatPaise

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLinkScreen(navController: NavController, viewModel: CreateLinkViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var amount by remember { mutableStateOf("") }
    var orderId by remember { mutableStateOf("") }
    var customerName by remember { mutableStateOf("") }
    var expiryMinutes by remember { mutableStateOf("15") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create payment link") },
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
            if (state.created == null) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount (₹)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = orderId,
                    onValueChange = { orderId = it },
                    label = { Text("Order ID") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = customerName,
                    onValueChange = { customerName = it },
                    label = { Text("Customer name (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )
                OutlinedTextField(
                    value = expiryMinutes,
                    onValueChange = { expiryMinutes = it },
                    label = { Text("Expiry (minutes)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                )
                state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                Button(
                    onClick = {
                        viewModel.create(amount, orderId, customerName.ifBlank { null }, expiryMinutes.toIntOrNull() ?: 15)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading,
                ) {
                    Text(if (state.isLoading) "Creating..." else "Create link")
                }
            } else {
                val link = state.created!!
                Text("Payment link created", style = MaterialTheme.typography.titleLarge)
                Text(formatPaise(link.amount, link.currency), style = MaterialTheme.typography.headlineMedium)
                Text(link.payment_link, style = MaterialTheme.typography.bodySmall)
                AsyncImage(
                    model = link.qr_url,
                    contentDescription = "QR code",
                    modifier = Modifier.fillMaxWidth().height(220.dp),
                    contentScale = ContentScale.Fit,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("link", link.payment_link))
                        },
                        modifier = Modifier.weight(1f),
                    ) { Text("Copy link") }
                    Button(
                        onClick = {
                            context.startActivity(
                                Intent.createChooser(
                                    Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_TEXT, link.payment_link)
                                    },
                                    "Share link",
                                ),
                            )
                        },
                        modifier = Modifier.weight(1f),
                    ) { Text("Share") }
                }
                TextButton(onClick = {
                    viewModel.reset()
                    navController.popBackStack()
                }) { Text("Done") }
            }
        }
    }
}
