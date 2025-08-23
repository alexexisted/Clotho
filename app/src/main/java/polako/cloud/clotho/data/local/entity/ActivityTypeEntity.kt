package polako.cloud.clotho.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import polako.cloud.clotho.domain.model.ActivityType

@Entity(tableName = "activity_type")
data class ActivityTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val iconRes: Int,
    val color: Int,
) {
    fun toDomainModel(): ActivityType =
        ActivityType(
            id = id,
            name = name,
            icon = iconRes,
            color = color,
        )

    companion object {
        fun fromDomainModelToEntity(
            activityType: ActivityType,
            id: Long = 0,
        ): ActivityTypeEntity =
            ActivityTypeEntity(
                id = id,
                name = activityType.name,
                iconRes = activityType.icon,
                color = activityType.color,
            )
    }
}
