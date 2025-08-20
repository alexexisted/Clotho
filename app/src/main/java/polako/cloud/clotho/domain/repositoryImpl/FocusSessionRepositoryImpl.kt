package polako.cloud.clotho.domain.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import polako.cloud.clotho.data.local.dao.ActivityTypeDao
import polako.cloud.clotho.data.local.dao.FocusSessionDao
import polako.cloud.clotho.data.local.entity.FocusSessionEntity
import polako.cloud.clotho.data.repository.FocusSessionRepository
import polako.cloud.clotho.domain.model.FocusSession
import java.time.LocalDateTime
import javax.inject.Inject

class FocusSessionRepositoryImpl @Inject constructor(
    private val focusSessionDao: FocusSessionDao,
    private val activityTypeDao: ActivityTypeDao
) : FocusSessionRepository {

    override suspend fun insertFocusSession(focusSession: FocusSession): Long {
        val entity = mapToEntity(focusSession)
        return focusSessionDao.insert(entity)
    }

//    override suspend fun insertAllFocusSessions(focusSessions: List<FocusSession>): List<Long> {
//        val entities = focusSessions.map { mapToEntity(it) }
//        return focusSessionDao.insertAll(entities)
//    }

    override suspend fun updateFocusSession(focusSession: FocusSession) {
        val entity = mapToEntity(focusSession)
        focusSessionDao.update(entity)
    }

    override suspend fun deleteFocusSession(focusSession: FocusSession) {
        val entity = mapToEntity(focusSession)
        focusSessionDao.delete(entity)
    }

    override fun getAllSessions(): Flow<List<FocusSession>> {
        return focusSessionDao.getAllSessions().map { entities ->
            entities.map { mapToDomain(it) }
        }
    }

    override suspend fun getSessionById(sessionId: Long): FocusSession? {
        val entity = focusSessionDao.getSessionById(sessionId) ?: return null
        return mapToDomain(entity)
    }

//    override fun getSessionsByActivityId(activityId: Long): Flow<List<FocusSession>> {
//        return focusSessionDao.getSessionsByActivityId(activityId).map { entities ->
//            entities.map { mapToDomain(it) }
//        }
//    }

//    override fun getSessionsByDateRange(
//        startDate: LocalDateTime,
//        endDate: LocalDateTime
//    ): Flow<List<FocusSession>> {
//        return focusSessionDao.getSessionsByDateRange(startDate, endDate).map { entities ->
//            entities.map { mapToDomain(it) }
//        }
//    }

    override suspend fun deleteSessionById(sessionId: Long) {
        focusSessionDao.deleteSessionById(sessionId)
    }

    override suspend fun deleteAllSessions() {
        focusSessionDao.deleteAllSessions()
    }

    private suspend fun mapToDomain(entity: FocusSessionEntity): FocusSession {
        val activityType = activityTypeDao.getActivityTypeById(entity.activityId)?.toDomainModel()

        return FocusSession(
            id = entity.sessionId,
            activityId = entity.activityId,
            activityType = activityType,
            startTime = entity.startTime,
            endTime = entity.endTime,
            duration = entity.duration,
            reflectionScore = entity.reflectionScore,
            reflectionNote = entity.reflectionNote
        )
    }

    private fun mapToEntity(domain: FocusSession): FocusSessionEntity {
        return FocusSessionEntity(
            sessionId = domain.id,
            activityId = domain.activityId,
            startTime = domain.startTime,
            endTime = domain.endTime,
            duration = domain.duration,
            reflectionScore = domain.reflectionScore,
            reflectionNote = domain.reflectionNote
        )
    }
}