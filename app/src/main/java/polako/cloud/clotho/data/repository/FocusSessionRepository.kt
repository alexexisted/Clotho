package polako.cloud.clotho.data.repository

import polako.cloud.clotho.domain.model.FocusSession

interface FocusSessionRepository {
    suspend fun insertFocusSession(focusSession: FocusSession): Long

    suspend fun updateFocusSession(focusSession: FocusSession)

    suspend fun deleteFocusSession(focusSession: FocusSession)

    fun getAllSessions(): List<FocusSession>

    suspend fun getSessionById(sessionId: Long): FocusSession?

    suspend fun deleteSessionById(sessionId: Long)

    suspend fun deleteAllSessions()
}