package polako.cloud.clotho.domain.repositoryImpl

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import polako.cloud.clotho.data.repository.FocusTimerGlobalUIState
import polako.cloud.clotho.data.repository.FocusTimerRepository
import polako.cloud.clotho.service.ActivityManager
import polako.cloud.clotho.service.FocusTimerForegroundService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FocusTimerRepositoryImpl
    @Inject
    constructor(
        private val context: Context,
        private val activityManager: ActivityManager,
    ) : FocusTimerRepository {
        @Suppress("ktlint:standard:backing-property-naming")
        private val _globalUIState = MutableStateFlow(FocusTimerGlobalUIState())
        override val globalUiState: StateFlow<FocusTimerGlobalUIState> = _globalUIState.asStateFlow()

        private var currentElapsedTimeMillis: Long = 0L

        override fun startTimer() {
            val intent =
                Intent(context, FocusTimerForegroundService::class.java).apply {
                    action = FocusTimerForegroundService.ACTION_START
                    putExtra(FocusTimerForegroundService.EXTRA_DURATION, 0L)
                }
            ContextCompat.startForegroundService(context, intent)

            _globalUIState.update {
                it.copy(
                    elapsedTimeMillis = currentElapsedTimeMillis,
                    activityType = activityManager.selectedActivity,
                    isRunning = true,
                )
            }
        }

        override fun stopTimer() {
            val intent =
                Intent(context, FocusTimerForegroundService::class.java).apply {
                    action = FocusTimerForegroundService.ACTION_STOP
                }
            ContextCompat.startForegroundService(context, intent)
            _globalUIState.update {
                it.copy(
                    isPaused = false,
                    isRunning = false,
                    elapsedTimeMillis = 0L,
                )
            }
        }

        override fun pauseTimer() {
            val intent =
                Intent(context, FocusTimerForegroundService::class.java).apply {
                    action = FocusTimerForegroundService.ACTION_PAUSE
                }
            ContextCompat.startForegroundService(context, intent)

            _globalUIState.update {
                it.copy(
                    isRunning = true,
                    isPaused = true,
                )
            }
        }

        override fun resumeTimer() {
            val intent =
                Intent(context, FocusTimerForegroundService::class.java).apply {
                    action = FocusTimerForegroundService.ACTION_RESUME
                }
            ContextCompat.startForegroundService(context, intent)

            _globalUIState.update {
                it.copy(
                    isRunning = true,
                    isPaused = false,
                )
            }
        }

        override fun onTick(elapsed: Long) {
            _globalUIState.update {
                it.copy(
                    elapsedTimeMillis = elapsed,
                )
            }
        }
    }
