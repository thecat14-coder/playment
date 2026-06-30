package com.gateway.android.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.android.data.repo.AuthRepository
import com.gateway.android.data.repo.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val needsUpiSetup: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val deviceRepo: DeviceRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        AuthUiState(
            isAuthenticated = authRepo.isLoggedIn(),
            needsUpiSetup = authRepo.isLoggedIn() && authRepo.needsUpiSetup(),
        ),
    )
    val state = _state.asStateFlow()

    fun login(email: String, password: String) {
        authenticate { authRepo.login(email, password) }
    }

    fun register(name: String, email: String, password: String, upiId: String?) {
        authenticate { authRepo.register(name, email, password, upiId) }
    }

    private fun authenticate(action: suspend () -> Result<Unit>) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val authResult = action()
            if (authResult.isFailure) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = authResult.exceptionOrNull()?.message ?: "Authentication failed",
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

            _state.value = _state.value.copy(
                isLoading = false,
                isAuthenticated = true,
                needsUpiSetup = authRepo.needsUpiSetup(),
            )
        }
    }
}
