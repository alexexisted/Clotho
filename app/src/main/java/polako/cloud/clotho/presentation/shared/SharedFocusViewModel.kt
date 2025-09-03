package polako.cloud.clotho.presentation.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import polako.cloud.clotho.data.repository.FocusTimerRepository
import polako.cloud.clotho.data.repository.FocusTimerUIAction
import javax.inject.Inject

/**
 * User taps “Start Focus” → FocusScreen → SharedFocusViewModel.startTimer() → launches FocusTimerForegroundService → updates FocusTimerRepository.
 *
 * Repository pushes new elapsed time → Shared VM observes → UI updates across all screens.
 *
 * When app is killed, Service + Notification keep timer running.
 *
 * When app restarts, Shared VM reads repository state → shows current timer.
 */
@HiltViewModel
class SharedFocusViewModel
    @Inject
    constructor(
        private val repository: FocusTimerRepository,
    ) : ViewModel() {
        val timerState: StateFlow<FocusTimerUIAction> = repository.uiStateTimer

        private val _stopwatchUiState = MutableStateFlow(SharedFocusUiState())
        val stopwatchUiState: StateFlow<SharedFocusUiState> = _stopwatchUiState.asStateFlow()

        init {
            timerState
                .onEach { state ->
                    when (state) {
                        is FocusTimerUIAction.Running ->
                            _stopwatchUiState.value =
                                SharedFocusUiState(
                                    elapsedTime = state.elapsedTimeMillis,
                                    isRunning = true,
                                    isPaused = false,
                                )

                        is FocusTimerUIAction.Paused ->
                            _stopwatchUiState.value =
                                SharedFocusUiState(
                                    elapsedTime = state.elapsedTimeMillis,
                                    isRunning = false,
                                    isPaused = true,
                                )

                        is FocusTimerUIAction.Idle ->
                            _stopwatchUiState.value =
                                SharedFocusUiState(
                                    elapsedTime = 0L,
                                    isRunning = false,
                                )

                        is FocusTimerUIAction.ElapsedTimeMillis ->
                            _stopwatchUiState.value =
                                _stopwatchUiState.value.copy(
                                    elapsedTime = state.elapsedTimeMillis,
                                )

                        is FocusTimerUIAction.RunningState ->
                            _stopwatchUiState.value =
                                _stopwatchUiState.value.copy(
                                    isRunning = state.isRunning && !state.isPaused,
                                )
                    }
                }.launchIn(viewModelScope)
        }

        fun startSession() {
            if (_stopwatchUiState.value.isPaused) {
                resumeSession()
            } else {
                repository.startTimer()
            }
        }

        fun pauseSession() {
            repository.pauseTimer()
        }

        fun resumeSession() {
            repository.resumeTimer()
        }

        fun stopSession() {
            repository.stopTimer()
        }
    }

data class SharedFocusUiState(
    val elapsedTime: Long = 0L,
    val isRunning: Boolean = false,
    val isPaused: Boolean = false,
    val isStopped: Boolean = false,
)
