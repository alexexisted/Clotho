package polako.cloud.clotho.presentation.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import polako.cloud.clotho.data.repository.FocusTimerGlobalUIState
import polako.cloud.clotho.data.repository.FocusTimerRepository
import polako.cloud.clotho.utils.execute
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
        val globalUiState: StateFlow<FocusTimerGlobalUIState> = repository.globalUiState

        private val _uiAction = MutableSharedFlow<SharedFocusUIAction>()
        val uiAction: SharedFlow<SharedFocusUIAction> = _uiAction.asSharedFlow()

        fun startSession() {
            if (globalUiState.value.isPaused) {
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

        fun navigateToFocusScreen() {
            viewModelScope.execute(
                source = { _uiAction.emit(SharedFocusUIAction.NavigateToFocusScreen) },
            )
        }
    }

sealed interface SharedFocusUIAction {
    object NavigateToFocusScreen : SharedFocusUIAction
}
