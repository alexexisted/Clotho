package polako.cloud.clotho.presentation.history_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import polako.cloud.clotho.data.repository.FocusSessionRepository
import polako.cloud.clotho.domain.model.FocusSession
import polako.cloud.clotho.utils.execute
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val focusSessionRepository: FocusSessionRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        showHistory()
    }

    fun showHistory() {
        viewModelScope.execute(
            source = {
                focusSessionRepository.getAllSessions()
            },
            onSuccess = { sessions ->
                _uiState.update {
                    it.copy(
                        sessions = sessions
                    )
                }
            }
        )
    }
}

data class HistoryUiState(
    val sessions: List<FocusSession> = emptyList()
)