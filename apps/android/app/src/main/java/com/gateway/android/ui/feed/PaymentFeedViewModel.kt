package com.gateway.android.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gateway.android.data.db.PendingEvidence
import com.gateway.android.data.repo.EvidenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FeedUiState(
    val notifications: List<PendingEvidence> = emptyList(),
)

@HiltViewModel
class PaymentFeedViewModel @Inject constructor(
    private val evidenceRepo: EvidenceRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(FeedUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            evidenceRepo.getAllPending().collect { list ->
                _state.value = FeedUiState(notifications = list)
            }
        }
    }
}
