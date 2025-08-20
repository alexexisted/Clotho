package polako.cloud.clotho.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import polako.cloud.clotho.data.local.entity.FocusSessionEntity
import java.time.LocalDateTime

@Dao
interface FocusSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(focusSession: FocusSessionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(focusSessions: List<FocusSessionEntity>): List<Long>

    @Update
    suspend fun update(focusSession: FocusSessionEntity)

    @Delete
    suspend fun delete(focusSession: FocusSessionEntity)

    @Query("SELECT * FROM focus_session")
    fun getAllSessions(): List<FocusSessionEntity>

    @Query("SELECT * FROM focus_session WHERE sessionId = :sessionId")
    suspend fun getSessionById(sessionId: Long): FocusSessionEntity?

    @Query("SELECT * FROM focus_session WHERE activityId = :activityId")
    fun getSessionsByActivityId(activityId: Long): Flow<List<FocusSessionEntity>>

    @Query("SELECT * FROM focus_session WHERE startTime BETWEEN :startDate AND :endDate")
    fun getSessionsByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): Flow<List<FocusSessionEntity>>

    @Query("DELETE FROM focus_session WHERE sessionId = :sessionId")
    suspend fun deleteSessionById(sessionId: Long)

    @Query("DELETE FROM focus_session")
    suspend fun deleteAllSessions()
}