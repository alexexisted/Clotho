package polako.cloud.clotho.presentation.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import polako.cloud.clotho.data.repository.ActivityTypeRepository
import polako.cloud.clotho.data.repository.FocusSessionRepository
import polako.cloud.clotho.domain.model.FocusSession
import polako.cloud.clotho.service.TimeTransformManager
import polako.cloud.clotho.utils.execute
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel
    @Inject
    constructor(
        private val activityTypeRepository: ActivityTypeRepository,
        private val focusSessionRepository: FocusSessionRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MainMenuUiState())
        val uiState: StateFlow<MainMenuUiState> = _uiState.asStateFlow()

        init {
            setupDb()
            getSessionsTime()
        }

        private fun setupDb() {
            viewModelScope.execute(
                source = {
                    activityTypeRepository.getAllActivities()
                },
            )
        }

        private fun getSessionsTime() {
            viewModelScope.execute(
                source = {
                    focusSessionRepository.getAllSessions()
                },
                onSuccess = { sessions ->
                    _uiState.update {
                        it.copy(
                            sessionsAmount = sessions.size,
                            sessionsDurationUIModel =
                                TimeTransformManager.formatElapsedTime(
                                    calculateSessionsTime(sessions),
                                ),
                        )
                    }
                },
            )
        }

        private fun calculateSessionsTime(sessions: List<FocusSession>): Long {
            var duration = 0L
            sessions.forEach { session ->
                duration += session.duration.toMillis()
            }
            return duration
        }
    }

data class MainMenuUiState(
    val isLoading: Boolean = false,
    val sessionsDurationUIModel: String = "0:0",
    val sessionsAmount: Int = 0,
)
