package com.gateway.android.ui.developer

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.android.data.api.ApiKeyData
import com.gateway.android.data.repo.MerchantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DeveloperUiState(
    val webhookUrl: String = "",
    val apiKeys: List<ApiKeyData> = emptyList(),
    val newApiKey: String? = null,
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val message: String? = null,
    val error: String? = null,
)

@HiltViewModel
class DeveloperViewModel @Inject constructor(
    private val merchantRepo: MerchantRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(DeveloperUiState())
    val state = _state.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val account = merchantRepo.getAccount().getOrNull()
            val keys = merchantRepo.listApiKeys().getOrNull() ?: emptyList()
            _state.value = DeveloperUiState(
                webhookUrl = account?.webhook_url.orEmpty(),
                apiKeys = keys,
                isLoading = false,
            )
        }
    }

    fun updateWebhook(url: String) {
        _state.value = _state.value.copy(webhookUrl = url)
    }

    fun saveWebhook() {
        viewModelScope.launch {
            val url = _state.value.webhookUrl.trim()
            if (url.isEmpty()) {
                _state.value = _state.value.copy(error = "Enter an HTTPS webhook URL")
                return@launch
            }
            _state.value = _state.value.copy(isSaving = true, error = null)
            val result = merchantRepo.updateAccount(webhookUrl = url)
            _state.value = _state.value.copy(
                isSaving = false,
                message = if (result.isSuccess) "Webhook saved" else null,
                error = result.exceptionOrNull()?.message,
            )
        }
    }

    fun regenerateApiKey() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isSaving = true, error = null, newApiKey = null)
            val result = merchantRepo.createApiKey("Android app")
            if (result.isSuccess) {
                val keys = merchantRepo.listApiKeys().getOrNull() ?: emptyList()
                _state.value = _state.value.copy(
                    isSaving = false,
                    newApiKey = result.getOrNull()?.key,
                    apiKeys = keys,
                    message = "Copy this key now — it won't be shown again",
                )
            } else {
                _state.value = _state.value.copy(
                    isSaving = false,
                    error = result.exceptionOrNull()?.message,
                )
            }
        }
    }

    fun testWebhook() {
        viewModelScope.launch {
            val result = merchantRepo.testWebhook()
            _state.value = _state.value.copy(
                message = result.getOrNull(),
                error = result.exceptionOrNull()?.message,
            )
        }
    }

    fun copyKey(context: Context, key: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("api_key", key))
        _state.value = _state.value.copy(message = "API key copied")
    }
}
