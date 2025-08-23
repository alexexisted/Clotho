package polako.cloud.clotho.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import polako.cloud.clotho.domain.model.FocusSession
import java.time.Duration
import java.time.LocalDateTime

@Entity(
    tableName = "focus_session",
    foreignKeys = [
        ForeignKey(
            entity = ActivityTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["activityId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FocusSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val sessionId: Long = 0,
    val activityId: Long,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val duration: Duration,
    val reflectionScore: Int,
    val reflectionNote: List<String>?
)

fun FocusSessionEntity.toFocusSessionUIModel(): FocusSession {
    return FocusSession(
        id = this.sessionId,
        activityId = this.activityId,
        activityType = null,
        startTime = this.startTime,
        endTime = this.endTime,
        duration = this.duration,
        reflectionScore = this.reflectionScore,
        reflectionNote = this.reflectionNote,
    )
}