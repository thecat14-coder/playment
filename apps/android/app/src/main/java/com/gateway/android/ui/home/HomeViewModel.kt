package com.gateway.android.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.android.data.repo.AuthRepository
import com.gateway.android.data.repo.DeviceRepository
import com.gateway.android.service.ForegroundService
import com.gateway.android.service.NotificationListenerService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isOnline: Boolean = false,
    val listenerRunning: Boolean = false,
    val lastHeartbeatAt: String? = null,
    val healthScore: Int = 0,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val deviceRepo: DeviceRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            while (true) {
                val deviceId = authRepo.getDeviceId()
                if (deviceId != null) {
                    val result = deviceRepo.sendHeartbeat(deviceId, NotificationListenerService.isRunning)
                    _state.value = HomeUiState(
                        isOnline = result.isSuccess,
                        listenerRunning = NotificationListenerService.isRunning,
                        healthScore = 100,
                    )
                }
                delay(30_000L)
            }
        }
    }

    fun logout() {
        authRepo.logout()
    }
}
