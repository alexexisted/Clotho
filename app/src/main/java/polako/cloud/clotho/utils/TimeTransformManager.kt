package polako.cloud.clotho.utils

import android.annotation.SuppressLint
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
object TimeTransformManager {
    @SuppressLint("DefaultLocale")
    fun formatElapsedTime(timeMillis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(timeMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeMillis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis) % 60

        return formatTimeComponents(hours, minutes, seconds)
    }

    private fun formatTimeComponents(
        hours: Long,
        minutes: Long,
        seconds: Long,
    ): String =
        when {
            hours > 0 -> {
                if (minutes > 0) "${hours}h ${minutes}m" else "${hours}h"
            }

            minutes > 0 -> {
                if (seconds > 0) "${minutes}m ${seconds}s" else "${minutes}m"
            }

            else -> "${seconds}s"
        }
}
