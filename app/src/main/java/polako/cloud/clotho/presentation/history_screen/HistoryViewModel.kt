package polako.cloud.clotho.presentation.history_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import polako.cloud.clotho.data.repository.FocusSessionRepository
import polako.cloud.clotho.domain.model.FocusSession
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val focusSessionRepository: FocusSessionRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    private fun showHistory() {

    }
}

data class HistoryUiState(
    val sessions: List<FocusSession> = emptyList()
)