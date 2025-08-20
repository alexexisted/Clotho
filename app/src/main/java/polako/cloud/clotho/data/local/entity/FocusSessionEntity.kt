package polako.cloud.clotho.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
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
    val reflectionNote: String?
)