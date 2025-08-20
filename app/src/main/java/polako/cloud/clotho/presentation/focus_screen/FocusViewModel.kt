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
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class FocusViewModel @Inject constructor(
    private val activityManager: ActivityManager,
    private val focusSessionRepository: FocusSessionRepository,
    private val activityTypeRepository: ActivityTypeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FocusUiState())
    val uiState: StateFlow<FocusUiState> = _uiState.asStateFlow()

    fun onAction(action: FocusUIAction) {
        when (action) {
            FocusUIAction.Start -> startTimer()
            FocusUIAction.Pause -> pauseTimer()
            FocusUIAction.Stop -> stopTimer()
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
                activity = activity
            )
        }
    }

    private fun startTimer() {
        if (_uiState.value.isRunning) return

        _uiState.update { it.copy(isRunning = true, isPaused = false) }

        timerJob = viewModelScope.launch {
            val startTime = System.currentTimeMillis() - _uiState.value.elapsedTimeMillis
            while (isActive) {
                val elapsed = System.currentTimeMillis() - startTime
                _uiState.update { it.copy(elapsedTimeMillis = elapsed) }
                delay(100L) // update every 100ms
            }
        }
    }

    private fun pauseTimer() {
        _uiState.update { it.copy(isRunning = false, isPaused = true) }
        timerJob?.cancel()
    }

    private fun stopTimer() {
        val currentState = _uiState.value
        val elapsedTime = currentState.elapsedTimeMillis
        
        _uiState.update {
            it.copy(
                isRunning = false,
                isPaused = false,
                isFinished = true,
                elapsedTimeMillis = 0L
            )
        }
        timerJob?.cancel()

        currentState.activity?.let { activity ->
            saveFocusSession(activity, elapsedTime)
        }


    }
    
    private fun saveFocusSession(activity: ActivityType, elapsedTimeMillis: Long) {
        viewModelScope.launch {
            try {
                val activityId = saveActivityAndGetId(activity)

                val now = LocalDateTime.now()
                val duration = Duration.ofMillis(elapsedTimeMillis)
                val startTime = now.minus(duration)
                
                val focusSession = FocusSession(
                    activityId = activityId,
                    startTime = startTime,
                    endTime = now,
                    duration = duration
                )
                
                focusSessionRepository.insertFocusSession(focusSession)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    private suspend fun saveActivityAndGetId(activity: ActivityType): Long {
        return activityTypeRepository.insertActivityType(activity)
    }

}

data class FocusUiState(
    val activity: ActivityType? = null,
    val isPaused: Boolean = false,
    val isFinished: Boolean = false,
    val isRunning: Boolean = false,
    val elapsedTimeMillis: Long = 0L
)