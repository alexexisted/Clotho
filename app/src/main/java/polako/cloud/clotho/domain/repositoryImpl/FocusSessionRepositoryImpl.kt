package polako.cloud.clotho.domain.repositoryImpl

import polako.cloud.clotho.data.local.dao.ActivityTypeDao
import polako.cloud.clotho.data.local.dao.FocusSessionDao
import polako.cloud.clotho.data.local.entity.FocusSessionEntity
import polako.cloud.clotho.data.local.entity.toFocusSessionUIModel
import polako.cloud.clotho.data.repository.FocusSessionRepository
import polako.cloud.clotho.domain.model.FocusSession
import polako.cloud.clotho.domain.model.FocusSessionUIModel
import javax.inject.Inject

class FocusSessionRepositoryImpl @Inject constructor(
    private val focusSessionDao: FocusSessionDao,
    private val activityTypeDao: ActivityTypeDao
) : FocusSessionRepository {

    override suspend fun insertFocusSession(focusSession: FocusSession): Long {
        val entity = mapToEntity(focusSession)
        return focusSessionDao.insert(entity)
    }

    override suspend fun updateFocusSession(focusSession: FocusSession) {
        val entity = mapToEntity(focusSession)
        focusSessionDao.update(entity)
    }

    override suspend fun deleteFocusSession(focusSession: FocusSession) {
        val entity = mapToEntity(focusSession)
        focusSessionDao.delete(entity)
    }

    override fun getAllSessions(): List<FocusSession> {
        return focusSessionDao.getAllSessions().map { it.toFocusSessionUIModel() }
    }

    override suspend fun getSessionById(sessionId: Long): FocusSession? {
        val entity = focusSessionDao.getSessionById(sessionId) ?: return null
        return mapToDomain(entity)
    }

    override suspend fun deleteSessionById(sessionId: Long) {
        focusSessionDao.deleteSessionById(sessionId)
    }

    override suspend fun deleteAllSessions() {
        focusSessionDao.deleteAllSessions()
    }
    
    override suspend fun getAllSessionsWithActivity(): List<FocusSession> {
        return focusSessionDao.getAllSessionsWithActivity().map { it.toFocusSession() }
    }
    
    override suspend fun getAllSessionsAsUIModels(): List<FocusSessionUIModel> {
        return focusSessionDao.getAllSessionsWithActivity().map { it.toFocusSessionUIModel() }
    }
    
    override suspend fun getSessionWithActivityById(sessionId: Long): FocusSession? {
        val sessionWithActivity = focusSessionDao.getSessionWithActivityById(sessionId) ?: return null
        return sessionWithActivity.toFocusSession()
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