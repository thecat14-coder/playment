package com.gateway.android.ui.payments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gateway.android.data.api.PaymentData
import com.gateway.android.ui.components.MerchantBottomBar
import com.gateway.android.ui.components.formatPaise
import com.gateway.android.ui.components.formatStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentsScreen(navController: NavController, viewModel: PaymentsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val filters = listOf(null to "All", "pending" to "Pending", "success" to "Success", "failed" to "Failed")

    Scaffold(
        topBar = { TopAppBar(title = { Text("Payments") }) },
        bottomBar = { MerchantBottomBar(navController, "payments") },
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            OutlinedTextField(
                value = state.search,
                onValueChange = {
                    viewModel.setSearch(it)
                    viewModel.load()
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search order ID or customer") },
                singleLine = true,
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(filters) { (value, label) ->
                    FilterChip(
                        selected = state.filter == value,
                        onClick = { viewModel.setFilter(value) },
                        label = { Text(label) },
                    )
                }
            }
            when {
                state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
                state.payments.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No payments found")
                }
                else -> LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(state.payments, key = { it.id }) { payment ->
                        PaymentRow(payment)
                    }
                }
            }
        }
    }
}

@Composable
private fun PaymentRow(payment: PaymentData) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(formatPaise(payment.amount, payment.currency), style = MaterialTheme.typography.titleMedium)
                Text(payment.order_id, style = MaterialTheme.typography.bodyMedium)
                payment.customer_name?.let { Text(it, style = MaterialTheme.typography.bodySmall) }
            }
            Text(formatStatus(payment.status), style = MaterialTheme.typography.labelLarge)
        }
    }
}
