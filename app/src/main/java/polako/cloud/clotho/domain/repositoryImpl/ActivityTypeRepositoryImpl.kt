package polako.cloud.clotho.domain.repositoryImpl

import polako.cloud.clotho.data.local.dao.ActivityTypeDao
import polako.cloud.clotho.data.local.entity.ActivityTypeEntity
import polako.cloud.clotho.data.repository.ActivityTypeRepository
import polako.cloud.clotho.domain.model.ActivityType
import javax.inject.Inject

class ActivityTypeRepositoryImpl @Inject constructor(
    private val activityTypeDao: ActivityTypeDao
) : ActivityTypeRepository {

    override suspend fun insertActivityType(activityType: ActivityType): Long {
        return activityTypeDao.insert(ActivityTypeEntity.fromDomainModel(activityType))
    }

    override suspend fun updateActivityType(activityType: ActivityType, id: Long) {
        activityTypeDao.update(ActivityTypeEntity.fromDomainModel(activityType, id))
    }

    override suspend fun deleteActivityType(activityType: ActivityType, id: Long) {
        activityTypeDao.delete(ActivityTypeEntity.fromDomainModel(activityType, id))
    }

    override suspend fun getActivityTypeById(id: Long): ActivityType? {
        return activityTypeDao.getActivityTypeById(id)?.toDomainModel()
    }

    override suspend fun deleteAllActivityTypes() {
        activityTypeDao.deleteAllActivityTypes()
    }
}