package polako.cloud.clotho.presentation.history_screen

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import polako.cloud.clotho.data.repository.FocusSessionRepository
import polako.cloud.clotho.domain.model.FocusSession
import polako.cloud.clotho.domain.model.FocusSessionWithDuration
import polako.cloud.clotho.domain.model.toSessionUIModelWithDuration
import polako.cloud.clotho.utils.execute
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel
    @Inject
    constructor(
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
                    focusSessionRepository.getAllSessionsWithActivity()
                },
                onSuccess = { sessions ->
                    _uiState.update {
                        it.copy(
                            sessions = sessions,
                        )
                    }
                    getSessionUIModels()
                },
            )
        }

        private fun getSessionUIModels() {
            viewModelScope.execute(
                source = {
                    focusSessionRepository.getAllSessionsAsUIModels()
                },
                onSuccess = { uiModels ->
                    _uiState.update {
                        it.copy(
                            uiModelSession =
                                uiModels.map { session ->
                                    session.toSessionUIModelWithDuration(
                                        formatDuration(session.duration),
                                    )
                                },
                        )
                    }
                },
            )
        }

        private fun formatDate(startTime: LocalDateTime): String {
            val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
            return startTime.format(formatter)
        }

        private fun formatDuration(duration: Duration): String {
            val hours = duration.toHours()
            val minutes =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    duration.toMinutesPart()
                } else {
                    TODO("VERSION.SDK_INT < S")
                }

            return if (hours > 0) {
                "$hours h $minutes min"
            } else {
                "$minutes min"
            }
        }
    }

data class HistoryUiState(
    val sessions: List<FocusSession> = emptyList(),
    val uiModelSession: List<FocusSessionWithDuration> = emptyList(),
)
