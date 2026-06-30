package com.gateway.android.ui.home

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.android.data.api.PaymentData
import com.gateway.android.data.repo.AuthRepository
import com.gateway.android.data.repo.DeviceRepository
import com.gateway.android.data.repo.MerchantRepository
import com.gateway.android.data.repo.OnlineStateRepository
import com.gateway.android.service.ForegroundService
import com.gateway.android.service.NotificationListenerService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

data class HomeUiState(
    val merchantName: String = "",
    val isOnline: Boolean = false,
    val isTogglingOnline: Boolean = false,
    val listenerRunning: Boolean = false,
    val healthScore: Int = 0,
    val todayCollectionPaise: Int = 0,
    val todayPaymentCount: Int = 0,
    val lastPayment: PaymentData? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val merchantRepo: MerchantRepository,
    private val deviceRepo: DeviceRepository,
    private val onlineStateRepo: OnlineStateRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            onlineStateRepo.isOnline.collectLatest { online ->
                _state.value = _state.value.copy(isOnline = online)
            }
        }
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val health = merchantRepo.getHealthSummary().getOrNull()
            val payments = merchantRepo.listPayments(page = 1, limit = 50).getOrNull()
            val today = LocalDate.now(ZoneId.systemDefault())
            val todaySuccess = payments?.data?.filter { payment ->
                payment.status == "success" && isToday(payment.paid_at ?: payment.created_at, today)
            } ?: emptyList()
            val lastPayment = payments?.data?.firstOrNull()

            _state.value = HomeUiState(
                merchantName = authRepo.getMerchantName() ?: "Merchant",
                isOnline = onlineStateRepo.isMerchantOnline(),
                listenerRunning = NotificationListenerService.isRunning,
                healthScore = health?.health_score ?: 0,
                todayCollectionPaise = todaySuccess.sumOf { it.amount },
                todayPaymentCount = todaySuccess.size,
                lastPayment = lastPayment,
                isLoading = false,
            )
        }
    }

    fun toggleOnline() {
        viewModelScope.launch {
            val goingOnline = !onlineStateRepo.isMerchantOnline()
            if (goingOnline) {
                val health = deviceRepo.getDeviceHealth()
                if (!health.notificationPermission) {
                    _state.value = _state.value.copy(
                        error = "Enable notification access in Device health before going online",
                    )
                    return@launch
                }
            }
            _state.value = _state.value.copy(isTogglingOnline = true, error = null)
            val result = onlineStateRepo.setOnline(goingOnline)
            if (result.isSuccess) {
                if (goingOnline) {
                    context.startForegroundService(Intent(context, ForegroundService::class.java))
                } else {
                    context.stopService(Intent(context, ForegroundService::class.java))
                }
                _state.value = _state.value.copy(
                    isOnline = goingOnline,
                    isTogglingOnline = false,
                )
            } else {
                _state.value = _state.value.copy(
                    isTogglingOnline = false,
                    error = result.exceptionOrNull()?.message,
                )
            }
        }
    }

    fun logout() {
        onlineStateRepo.clear()
        context.stopService(Intent(context, ForegroundService::class.java))
        authRepo.logout()
    }

    private fun isToday(iso: String, today: LocalDate): Boolean {
        return try {
            val date = Instant.parse(iso).atZone(ZoneId.systemDefault()).toLocalDate()
            date == today
        } catch (_: Exception) {
            false
        }
    }
}
