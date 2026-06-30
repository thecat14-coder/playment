package com.gateway.android.ui.links

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.gateway.android.data.api.PaymentData
import com.gateway.android.ui.components.MerchantBottomBar
import com.gateway.android.ui.components.formatPaise
import com.gateway.android.ui.components.formatStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentLinksScreen(navController: NavController, viewModel: PaymentLinksViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar(title = { Text("Payment links") }) },
        bottomBar = { MerchantBottomBar(navController, "links") },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("links/create") }) {
                Icon(Icons.Default.Add, contentDescription = "Create")
            }
        },
    ) { padding ->
        when {
            state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            state.error != null -> Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(state.error!!, color = MaterialTheme.colorScheme.error)
            }
            state.links.isEmpty() -> Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No payment links yet")
            }
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(state.links, key = { it.id }) { link ->
                    PaymentLinkCard(
                        link = link,
                        onCopy = { copyLink(context, link.payment_link ?: "") },
                        onShare = { shareLink(context, link.payment_link ?: "") },
                        onClick = { shareLink(context, link.payment_link ?: "") },
                    )
                }
            }
        }
    }
}

@Composable
private fun PaymentLinkCard(
    link: PaymentData,
    onCopy: () -> Unit,
    onShare: () -> Unit,
    onClick: () -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(formatPaise(link.amount, link.currency), style = MaterialTheme.typography.titleMedium)
                AssistChip(onClick = {}, label = { Text(formatStatus(link.status)) })
            }
            Text(link.order_id, style = MaterialTheme.typography.bodyMedium)
            link.customer_name?.let {
                Text(it, style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onCopy) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Copy")
                }
                TextButton(onClick = onShare) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Share")
                }
            }
        }
    }
}

private fun copyLink(context: Context, link: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText("payment_link", link))
}

private fun shareLink(context: Context, link: String) {
    context.startActivity(
        Intent.createChooser(
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, link)
            },
            "Share payment link",
        ),
    )
}
