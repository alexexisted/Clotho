package polako.cloud.clotho.presentation.focus_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import polako.cloud.clotho.data.repository.ActivityTypeRepository
import polako.cloud.clotho.data.repository.FocusSessionRepository
import polako.cloud.clotho.domain.ActivityManager
import polako.cloud.clotho.domain.model.ActivityType
import polako.cloud.clotho.domain.model.FocusSession
import polako.cloud.clotho.utils.execute
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class FocusViewModel
    @Inject
    constructor(
        private val activityManager: ActivityManager,
        private val focusSessionRepository: FocusSessionRepository,
        private val activityTypeRepository: ActivityTypeRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(FocusUiState())
        val uiState: StateFlow<FocusUiState> = _uiState.asStateFlow()

        fun onAction(action: FocusUIAction) {
            when (action) {
                FocusUIAction.Start -> startTimer()
                FocusUIAction.Pause -> pauseTimer()
                FocusUIAction.Stop -> stopTimer()
                FocusUIAction.ShowReflection -> showReflection()
                is FocusUIAction.SaveReflection -> saveReflection(action.score, action.tags)
                FocusUIAction.DismissReflection -> dismissReflection()
                FocusUIAction.OnSuccess -> {}
            }
        }

        private var timerJob: Job? = null

        init {
            activityManager.selectedActivity?.let { activity ->
                setActivity(activity)
            }
        }

        fun setActivity(activity: ActivityType) {
            _uiState.update {
                it.copy(
                    activity = activity,
                )
            }
        }

        private fun startTimer() {
            if (_uiState.value.isRunning) return

            _uiState.update { it.copy(isRunning = true, isPaused = false) }

            timerJob =
                viewModelScope.launch {
                    val startTime = System.currentTimeMillis() - _uiState.value.elapsedTimeMillis
                    while (isActive) {
                        val elapsed = System.currentTimeMillis() - startTime
                        _uiState.update { it.copy(elapsedTimeMillis = elapsed) }
                        delay(100L)
                    }
                }
        }

        private fun pauseTimer() {
            _uiState.update { it.copy(isRunning = false, isPaused = true) }
            timerJob?.cancel()
        }

        private fun stopTimer() {
            _uiState.update {
                it.copy(
                    isRunning = false,
                    isPaused = false,
                    isFinished = true,
                )
            }
            timerJob?.cancel()

            showReflection()
        }

        private fun showReflection() {
            _uiState.update {
                it.copy(
                    showReflectionBottomSheet = true,
                    initialReflectionScore = 5F,
                    selectedTags = emptyList(),
                )
            }
        }

        private fun dismissReflection() {
            _uiState.update {
                it.copy(
                    showReflectionBottomSheet = false,
                    lastSessionId = null,
                )
            }
        }

        private fun saveReflection(
            score: Int,
            tags: List<String>,
        ) {
            _uiState.update {
                it.copy(
                    finalReflectionScore = score,
                    selectedTags = tags,
                )
            }

            saveFocusSession()
        }

        private fun saveFocusSession() {
            val activityId = uiState.value.activity?.id ?: 0
            val now = LocalDateTime.now()
            val duration = Duration.ofMillis(uiState.value.elapsedTimeMillis)
            val startTime = now.minus(duration)
            val focusSession =
                FocusSession(
                    activityId = activityId,
                    startTime = startTime,
                    endTime = now,
                    duration = duration,
                    reflectionScore = _uiState.value.finalReflectionScore,
                    reflectionNote = _uiState.value.selectedTags,
                )
            createFocusSession(focusSession)
        }

        private fun createFocusSession(session: FocusSession) {
            viewModelScope.execute(
                source = {
                    focusSessionRepository.insertFocusSession(session)
                },
                onSuccess = {
                },
                onError = {
                },
            )
        }
    }

data class FocusUiState(
    val activity: ActivityType? = null,
    val isPaused: Boolean = false,
    val isFinished: Boolean = false,
    val isRunning: Boolean = false,
    val elapsedTimeMillis: Long = 0L,
    val showReflectionBottomSheet: Boolean = false,
    val initialReflectionScore: Float = 5F,
    val finalReflectionScore: Int = 5,
    val reflectionChips: List<String> =
        listOf(
            "Focused",
            "Distracted",
            "Energized",
            "Tired",
            "Motivated",
            "Bored",
            "Productive",
            "Stressed",
            "Calm",
            "Restless",
            "Confident",
            "Frustrated",
            "Creative",
            "Flow",
            "Overwhelmed",
        ),
    val selectedTags: List<String> = emptyList(),
    val lastSessionId: Long? = null,
)
