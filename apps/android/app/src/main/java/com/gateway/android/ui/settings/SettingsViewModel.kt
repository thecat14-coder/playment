package com.gateway.android.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.android.data.repo.AuthRepository
import com.gateway.android.data.repo.MerchantRepository
import com.gateway.android.data.repo.OnlineStateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import android.content.Intent
import com.gateway.android.service.ForegroundService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val name: String = "",
    val email: String = "",
    val upiId: String = "",
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val saved: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val merchantRepo: MerchantRepository,
    private val onlineStateRepo: OnlineStateRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsUiState())
    val state = _state.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val account = merchantRepo.getAccount().getOrNull()
            _state.value = SettingsUiState(
                name = account?.name ?: authRepo.getMerchantName().orEmpty(),
                email = account?.email ?: authRepo.getMerchantEmail().orEmpty(),
                upiId = account?.upi_id ?: authRepo.getMerchantUpiId().orEmpty(),
                isLoading = false,
            )
        }
    }

    fun updateName(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun updateUpi(upiId: String) {
        _state.value = _state.value.copy(upiId = upiId)
    }

    fun save() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isSaving = true, error = null, saved = false)
            val result = merchantRepo.updateAccount(
                name = _state.value.name.trim(),
                upiId = _state.value.upiId.trim(),
            )
            _state.value = if (result.isSuccess) {
                _state.value.copy(isSaving = false, saved = true)
            } else {
                _state.value.copy(
                    isSaving = false,
                    error = result.exceptionOrNull()?.message ?: "Save failed",
                )
            }
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        onlineStateRepo.clear()
        context.stopService(Intent(context, ForegroundService::class.java))
        authRepo.logout()
        onLoggedOut()
    }
}
