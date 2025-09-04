package polako.cloud.clotho.presentation.focus_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import polako.cloud.clotho.data.repository.FocusSessionRepository
import polako.cloud.clotho.domain.model.ActivityType
import polako.cloud.clotho.domain.model.FocusSession
import polako.cloud.clotho.service.ActivityManager
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
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(FocusUiState())
        val uiState: StateFlow<FocusUiState> = _uiState.asStateFlow()

        fun onAction(action: FocusUIAction) {
            when (action) {
                is FocusUIAction.ShowReflection -> showReflection(action.elapsedTimeMillis)
                is FocusUIAction.SaveReflection -> saveReflection(action.score, action.tags)
                FocusUIAction.DismissReflection -> dismissReflection()
                FocusUIAction.OnSuccess -> {}
            }
        }

        init {
            activityManager.selectedActivity?.let { activity ->
                setActivity(activity)
            }
        }

        fun setActivity(activity: ActivityType) {
            _uiState.update {
                Log.d("DEBUG-FOCUSl", activity.toString())
                it.copy(
                    activity = activity,
                )
            }
        }

        fun showReflection(elapsedTime: Long) {
            _uiState.update {
                it.copy(
                    showReflectionBottomSheet = true,
                    initialReflectionScore = 5F,
                    selectedTags = emptyList(),
                    elapsedTimeMillis = elapsedTime,
                )
            }
        }

        private fun dismissReflection() {
            _uiState.update {
                it.copy(
                    showReflectionBottomSheet = false,
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
)
