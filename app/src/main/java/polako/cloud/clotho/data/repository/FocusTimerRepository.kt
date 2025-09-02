package polako.cloud.clotho.data.repository

import kotlinx.coroutines.flow.StateFlow

interface FocusTimerRepository {
    val uiStateTimer: StateFlow<FocusTimerUIAction>

    fun startTimer()

    fun stopTimer()

    fun pauseTimer()

    fun resumeTimer()

    fun onTick(elapsed: Long)
}

sealed class FocusTimerUIAction {
    data class ElapsedTimeMillis(
        val elapsedTimeMillis: Long,
    ) : FocusTimerUIAction()

    data class RunningState(
        val isPaused: Boolean,
        val isRunning: Boolean,
    ) : FocusTimerUIAction()

    data object Idle : FocusTimerUIAction()

    data class Running(
        val elapsedTimeMillis: Long,
    ) : FocusTimerUIAction()

    data class Paused(
        val elapsedTimeMillis: Long,
    ) : FocusTimerUIAction()
}
