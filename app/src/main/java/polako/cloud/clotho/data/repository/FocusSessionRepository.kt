package polako.cloud.clotho.data.repository

import polako.cloud.clotho.domain.model.FocusSession
import polako.cloud.clotho.domain.model.FocusSessionUIModel

interface FocusSessionRepository {
    suspend fun insertFocusSession(focusSession: FocusSession): Long

    suspend fun updateFocusSession(focusSession: FocusSession)

    suspend fun updateFocusSessionReflection(
        sessionId: Long,
        reflectionScore: Int,
        reflectionNote: List<String>,
    )

    suspend fun deleteFocusSession(focusSession: FocusSession)

    fun getAllSessions(): List<FocusSession>

    suspend fun getSessionById(sessionId: Long): FocusSession?

    suspend fun deleteSessionById(sessionId: Long)

    suspend fun deleteAllSessions()

    suspend fun getAllSessionsWithActivity(): List<FocusSession>

    suspend fun getAllSessionsAsUIModels(): List<FocusSessionUIModel>

    suspend fun getSessionWithActivityById(sessionId: Long): FocusSession?
}
