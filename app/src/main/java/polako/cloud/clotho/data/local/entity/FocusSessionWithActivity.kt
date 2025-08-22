package polako.cloud.clotho.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import polako.cloud.clotho.domain.model.ActivityType
import polako.cloud.clotho.domain.model.FocusSession
import polako.cloud.clotho.domain.model.FocusSessionUIModel
import java.time.Duration

data class FocusSessionWithActivity(
    @Embedded val focusSession: FocusSessionEntity,
    @Relation(
        parentColumn = "activityId",
        entityColumn = "id"
    )
    val activityType: ActivityTypeEntity
) {
    fun toFocusSession(): FocusSession {
        return FocusSession(
            id = focusSession.sessionId,
            activityId = focusSession.activityId,
            activityType = activityType.toDomainModel(),
            startTime = focusSession.startTime,
            endTime = focusSession.endTime,
            duration = focusSession.duration,
            reflectionScore = focusSession.reflectionScore,
            reflectionNote = focusSession.reflectionNote
        )
    }

    fun toFocusSessionUIModel(): FocusSessionUIModel {
        return FocusSessionUIModel(
            id = focusSession.sessionId,
            activityType = activityType.toDomainModel(),
            duration = focusSession.duration,
            reflectionScore = focusSession.reflectionScore
        )
    }
}