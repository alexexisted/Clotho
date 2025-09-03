package polako.cloud.clotho.domain.repositoryImpl

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import polako.cloud.clotho.data.repository.FocusTimerRepository
import polako.cloud.clotho.data.repository.FocusTimerUIAction
import polako.cloud.clotho.service.FocusTimerForegroundService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FocusTimerRepositoryImpl
    @Inject
    constructor(
        private val context: Context,
    ) : FocusTimerRepository {
        @Suppress("ktlint:standard:backing-property-naming")
        private val _uiStateTimer = MutableStateFlow<FocusTimerUIAction>(FocusTimerUIAction.Idle)
        override val uiStateTimer: StateFlow<FocusTimerUIAction> = _uiStateTimer.asStateFlow()

        private var currentElapsedTimeMillis: Long = 0L
        private var isRunning: Boolean = false
        private var isPaused: Boolean = false

        override fun startTimer() {
            val intent =
                Intent(context, FocusTimerForegroundService::class.java).apply {
                    action = FocusTimerForegroundService.ACTION_START
                    putExtra(FocusTimerForegroundService.EXTRA_DURATION, 0L)
                }
            ContextCompat.startForegroundService(context, intent)

            isRunning = true
            isPaused = false
            currentElapsedTimeMillis = 0L

            _uiStateTimer.value = FocusTimerUIAction.Running(elapsedTimeMillis = currentElapsedTimeMillis)
        }

        override fun stopTimer() {
            val intent =
                Intent(context, FocusTimerForegroundService::class.java).apply {
                    action = FocusTimerForegroundService.ACTION_STOP
                }
            ContextCompat.startForegroundService(context, intent)
            _uiStateTimer.value = FocusTimerUIAction.Idle
        }

        override fun pauseTimer() {
            val intent =
                Intent(context, FocusTimerForegroundService::class.java).apply {
                    action = FocusTimerForegroundService.ACTION_PAUSE
                }
            ContextCompat.startForegroundService(context, intent)

            val currentState = uiStateTimer.value
            if (currentState is FocusTimerUIAction.Running) {
                _uiStateTimer.value = FocusTimerUIAction.Paused(elapsedTimeMillis = currentState.elapsedTimeMillis)
            }
        }

        override fun resumeTimer() {
            val intent =
                Intent(context, FocusTimerForegroundService::class.java).apply {
                    action = FocusTimerForegroundService.ACTION_RESUME
                }
            ContextCompat.startForegroundService(context, intent)

            val currentState = uiStateTimer.value
            if (currentState is FocusTimerUIAction.Paused) {
                _uiStateTimer.value = FocusTimerUIAction.Running(elapsedTimeMillis = currentState.elapsedTimeMillis)
            }
        }

        override fun onTick(elapsed: Long) {
            val currentState = uiStateTimer.value
            when (currentState) {
                is FocusTimerUIAction.Running -> {
                    _uiStateTimer.value = FocusTimerUIAction.Running(elapsedTimeMillis = elapsed)
                }

                is FocusTimerUIAction.Paused -> {
                    _uiStateTimer.value = FocusTimerUIAction.Paused(elapsedTimeMillis = elapsed)
                }

                else -> {
                    _uiStateTimer.value = FocusTimerUIAction.ElapsedTimeMillis(elapsed)
                }
            }
        }
    }
