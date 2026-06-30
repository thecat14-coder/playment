package com.gateway.android.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.android.data.repo.AuthRepository
import com.gateway.android.data.repo.DeviceRepository
import com.gateway.android.service.ForegroundService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val deviceRepo: DeviceRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState(isLoggedIn = authRepo.isLoggedIn()))
    val state = _state.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val loginResult = authRepo.login(email, password)
            if (loginResult.isFailure) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = loginResult.exceptionOrNull()?.message ?: "Login failed",
                )
                return@launch
            }

            val registerResult = deviceRepo.registerDevice()
            if (registerResult.isFailure) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Device registration failed: ${registerResult.exceptionOrNull()?.message}",
                )
                return@launch
            }

            context.startForegroundService(Intent(context, ForegroundService::class.java))

            _state.value = _state.value.copy(isLoading = false, isLoggedIn = true)
        }
    }
}
