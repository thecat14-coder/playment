package com.gateway.android.ui.links

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.android.data.api.PaymentLinkData
import com.gateway.android.data.repo.MerchantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateLinkUiState(
    val isLoading: Boolean = false,
    val created: PaymentLinkData? = null,
    val error: String? = null,
)

@HiltViewModel
class CreateLinkViewModel @Inject constructor(
    private val merchantRepo: MerchantRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(CreateLinkUiState())
    val state = _state.asStateFlow()

    fun create(amountRupees: String, orderId: String, customerName: String?, expiryMinutes: Int) {
        viewModelScope.launch {
            _state.value = CreateLinkUiState(isLoading = true)
            val paise = ((amountRupees.toDoubleOrNull() ?: 0.0) * 100).toInt()
            if (paise < 100) {
                _state.value = CreateLinkUiState(error = "Minimum amount is ₹1")
                return@launch
            }
            if (orderId.isBlank()) {
                _state.value = CreateLinkUiState(error = "Order ID is required")
                return@launch
            }
            val result = merchantRepo.createPaymentLink(
                amountPaise = paise,
                orderId = orderId.trim(),
                customerName = customerName,
                expiresInSeconds = expiryMinutes * 60,
            )
            _state.value = if (result.isSuccess) {
                CreateLinkUiState(created = result.getOrNull())
            } else {
                CreateLinkUiState(error = result.exceptionOrNull()?.message ?: "Failed to create link")
            }
        }
    }

    fun reset() {
        _state.value = CreateLinkUiState()
    }
}
