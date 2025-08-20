package polako.cloud.clotho.data.repository

import polako.cloud.clotho.domain.model.ActivityType

interface ActivityTypeRepository {
    suspend fun insertActivityType(activityType: ActivityType): Long

    suspend fun updateActivityType(activityType: ActivityType, id: Long)

    suspend fun deleteActivityType(activityType: ActivityType, id: Long)

    suspend fun getActivityTypeById(id: Long): ActivityType?

    suspend fun getAllActivities(): List<ActivityType>

    suspend fun deleteAllActivityTypes()
}