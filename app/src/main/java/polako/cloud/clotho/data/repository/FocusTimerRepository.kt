package polako.cloud.clotho.data.repository

import kotlinx.coroutines.flow.StateFlow
import polako.cloud.clotho.domain.model.ActivityType

interface FocusTimerRepository {
    val globalUiState: StateFlow<FocusTimerGlobalUIState>

    fun startTimer()

    fun stopTimer()

    fun pauseTimer()

    fun resumeTimer()

    fun onTick(elapsed: Long)
}

data class FocusTimerGlobalUIState(
    val elapsedTimeMillis: Long = 0L,
    val elapsedTimeUIModel: String = "0",
    val activityType: ActivityType? = null,
    val isPaused: Boolean = false,
    val isRunning: Boolean = false,
)
