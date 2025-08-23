package polako.cloud.clotho.domain.model

import java.time.Duration
import java.time.LocalDateTime

data class FocusSession(
    val id: Long = 0,
    val activityId: Long,
    val activityType: ActivityType? = null,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val duration: Duration,
    val reflectionScore: Int = 0,
    val reflectionNote: List<String>? = null,
)
