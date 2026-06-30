package com.gateway.android.ui.links

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.android.data.api.PaymentData
import com.gateway.android.data.repo.MerchantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PaymentLinksUiState(
    val links: List<PaymentData> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)

@HiltViewModel
class PaymentLinksViewModel @Inject constructor(
    private val merchantRepo: MerchantRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(PaymentLinksUiState())
    val state = _state.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = merchantRepo.listPaymentLinks()
            _state.value = if (result.isSuccess) {
                PaymentLinksUiState(links = result.getOrNull()?.data ?: emptyList(), isLoading = false)
            } else {
                PaymentLinksUiState(isLoading = false, error = result.exceptionOrNull()?.message)
            }
        }
    }
}
