package com.gateway.android.ui.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.android.data.api.PaymentData
import com.gateway.android.data.repo.MerchantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PaymentsUiState(
    val payments: List<PaymentData> = emptyList(),
    val filter: String? = null,
    val search: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
)

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val merchantRepo: MerchantRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(PaymentsUiState())
    val state = _state.asStateFlow()

    init { load() }

    fun setFilter(status: String?) {
        _state.value = _state.value.copy(filter = status)
        load()
    }

    fun setSearch(query: String) {
        _state.value = _state.value.copy(search = query)
    }

    fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = merchantRepo.listPayments(status = _state.value.filter)
            val filtered = result.getOrNull()?.data?.filter { payment ->
                val q = _state.value.search.trim().lowercase()
                q.isEmpty() ||
                    payment.order_id.lowercase().contains(q) ||
                    (payment.customer_name?.lowercase()?.contains(q) == true) ||
                    payment.id.lowercase().contains(q)
            } ?: emptyList()
            _state.value = PaymentsUiState(
                payments = filtered,
                filter = _state.value.filter,
                search = _state.value.search,
                isLoading = false,
                error = result.exceptionOrNull()?.message,
            )
        }
    }
}
