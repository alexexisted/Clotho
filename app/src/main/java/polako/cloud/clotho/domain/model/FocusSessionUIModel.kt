package polako.cloud.clotho.domain.model

import java.time.Duration

data class FocusSessionUIModel(
    val id: Long,
    val activityType: ActivityType? = null,
    val duration: Duration,
    val reflectionScore: Int = 0,
    val color: Int,
)

fun FocusSessionUIModel.toSessionUIModelWithDuration(textDuration: String): FocusSessionWithDuration =
    FocusSessionWithDuration(
        id,
        activityType,
        textDuration,
        reflectionScore,
        color,
    )
