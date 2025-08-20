package polako.cloud.clotho.data.repository

import kotlinx.coroutines.flow.Flow
import polako.cloud.clotho.domain.model.FocusSession

interface FocusSessionRepository {
    suspend fun insertFocusSession(focusSession: FocusSession): Long

    suspend fun updateFocusSession(focusSession: FocusSession)

    suspend fun deleteFocusSession(focusSession: FocusSession)

    fun getAllSessions(): Flow<List<FocusSession>>

    suspend fun getSessionById(sessionId: Long): FocusSession?

    suspend fun deleteSessionById(sessionId: Long)

    suspend fun deleteAllSessions()
}