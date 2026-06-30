package com.gateway.android.ui.setup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gateway.android.data.repo.MerchantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UpiSetupUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val saved: Boolean = false,
)

@HiltViewModel
class UpiSetupViewModel @Inject constructor(
    private val merchantRepo: MerchantRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(UpiSetupUiState())
    val state = _state.asStateFlow()

    fun saveUpi(upiId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = merchantRepo.updateAccount(upiId = upiId.trim())
            _state.value = if (result.isSuccess) {
                UpiSetupUiState(saved = true)
            } else {
                UpiSetupUiState(error = result.exceptionOrNull()?.message ?: "Failed to save UPI ID")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpiSetupScreen(navController: NavController, viewModel: UpiSetupViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    var upiId by remember { mutableStateOf("") }

    LaunchedEffect(state.saved) {
        if (state.saved) {
            navController.navigate("home") { popUpTo("upi_setup") { inclusive = true } }
        }
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Add UPI ID") }) }) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
        ) {
            Text(
                "Add your UPI ID to receive payments. Customers will pay to this UPI when they open your payment links.",
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = upiId,
                onValueChange = { upiId = it },
                label = { Text("UPI ID") },
                placeholder = { Text("yourname@ybl") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(16.dp))
            state.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(
                onClick = { viewModel.saveUpi(upiId) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading && upiId.length >= 3,
            ) {
                Text(if (state.isLoading) "Saving..." else "Save & continue")
            }
        }
    }
}
