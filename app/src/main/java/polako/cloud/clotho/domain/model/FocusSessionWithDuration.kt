package polako.cloud.clotho.domain.model

data class FocusSessionWithDuration(
    val id: Long,
    val activityType: ActivityType? = null,
    val textDuration: String,
    val reflectionScore: Int = 0
)
