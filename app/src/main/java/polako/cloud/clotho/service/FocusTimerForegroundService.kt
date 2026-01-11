package polako.cloud.clotho.service

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import polako.cloud.clotho.MainActivity
import polako.cloud.clotho.R
import polako.cloud.clotho.data.repository.FocusTimerRepository
import javax.inject.Inject

@AndroidEntryPoint
class FocusTimerForegroundService : Service() {
    @Inject
    lateinit var focusTimerRepository: FocusTimerRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var timerJob: Job? = null
    private var startTimeMillis: Long = 0L
    private var elapsedTimeMillis: Long = 0L
    private var hasNotificationPermission: Boolean = false

    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "FocusTimerChannel"

        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        const val EXTRA_DURATION = "EXTRA_DURATION"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_RESUME = "ACTION_RESUME"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        checkNotificationPermission()
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionStatus =
                applicationContext.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
            hasNotificationPermission = permissionStatus == android.content.pm.PackageManager.PERMISSION_GRANTED
        } else {
            hasNotificationPermission = true
        }
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        when (intent?.action) {
            ACTION_START -> startTimer()
            ACTION_STOP -> stopTimer()
            ACTION_PAUSE -> pauseTimer()
            ACTION_RESUME -> resumeTimer()
        }
        return START_STICKY
    }

    private fun startTimer() {
        if (timerJob != null) return
        startTimeMillis = System.currentTimeMillis()

        checkNotificationPermission()

        if (hasNotificationPermission) {
            val initialNotification = createNotification(0L)
            startForeground(NOTIFICATION_ID, initialNotification)
        } else {
            startForeground(NOTIFICATION_ID, createSilentNotification())
        }

        timerJob = launchTimer(startTimeMillis)
    }

    private fun createSilentNotification(): Notification =
        NotificationCompat
            .Builder(this, CHANNEL_ID)
            .setContentTitle("Focus Timer")
            .setContentText("Running in background")
            .setSmallIcon(R.drawable.stress_management_icon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .build()

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        stopForeground(true)
        stopSelf()
    }

    private fun pauseTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun resumeTimer() {
        if (timerJob != null) return
        startTimeMillis = System.currentTimeMillis() - elapsedTimeMillis

        checkNotificationPermission()

        if (hasNotificationPermission) {
            val initialNotification = createNotification(elapsedTimeMillis)
            startForeground(NOTIFICATION_ID, initialNotification)
        } else {
            startForeground(NOTIFICATION_ID, createSilentNotification())
        }

        timerJob = launchTimer(startTimeMillis)
    }

    private fun launchTimer(startTimeMillis: Long): Job? {
        timerJob =
            serviceScope.launch {
                while (isActive) {
                    elapsedTimeMillis = System.currentTimeMillis() - startTimeMillis
                    focusTimerRepository.onTick(elapsedTimeMillis)

                    if (hasNotificationPermission) {
                        val notification = createNotification(elapsedTimeMillis)
                        startForeground(NOTIFICATION_ID, notification)
                    }

                    delay(1000)
                }
            }
        return timerJob
    }

    private fun createNotification(elapsed: Long): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val minutes = (elapsed / 1000) / 60
        val seconds = (elapsed / 1000) % 60
        val timeText =
            if (elapsed >= 60000) {
                String.format("%02d:%02d", minutes, seconds)
            } else {
                "${elapsed / 1000} sec"
            }

        return NotificationCompat
            .Builder(this, CHANNEL_ID)
            .setContentTitle("Focus Timer")
            .setContentText("Running: $timeText")
            .setSmallIcon(R.drawable.stress_management_icon)
            .setContentIntent(pendingIntent)
            .setSilent(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .build()
    }

    private fun createNotificationChannel() {
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                "Focus Timer",
                NotificationManager.IMPORTANCE_LOW,
            )
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
