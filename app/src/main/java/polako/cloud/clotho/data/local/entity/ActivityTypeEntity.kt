package polako.cloud.clotho.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import polako.cloud.clotho.domain.model.ActivityType

@Entity(tableName = "activity_type")
data class ActivityTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val iconRes: Int
) {
    fun toDomainModel(): ActivityType {
        return ActivityType(
            name = name,
            icon = iconRes
        )
    }

    companion object {
        fun fromDomainModelToEntity(activityType: ActivityType, id: Long = 0): ActivityTypeEntity {
            return ActivityTypeEntity(
                id = id,
                name = activityType.name,
                iconRes = activityType.icon
            )
        }
    }
}