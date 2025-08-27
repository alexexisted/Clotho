package polako.cloud.clotho.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import polako.cloud.clotho.data.local.dao.ActivityTypeDao
import polako.cloud.clotho.data.local.dao.FocusSessionDao
import polako.cloud.clotho.data.local.entity.ActivityTypeEntity
import polako.cloud.clotho.data.local.entity.FocusSessionEntity
import polako.cloud.clotho.domain.model.ActivityType
import java.time.Duration
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var activityTypeDao: ActivityTypeDao
    private lateinit var focusSessionDao: FocusSessionDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room
                .inMemoryDatabaseBuilder(
                    context,
                    AppDatabase::class.java,
                ).build()
        activityTypeDao = db.activityTypeDao()
        focusSessionDao = db.focusSessionDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertAndReadActivityType() =
        runBlocking {
            val activityType =
                ActivityTypeEntity(
                    name = "Work",
                    iconRes = 123,
                    color = 0xFF607D8B.toInt(),
                )
            val id = activityTypeDao.insert(activityType)

            val retrievedActivityType = activityTypeDao.getActivityTypeById(id)

            assertNotNull(retrievedActivityType)
            assertEquals("Work", retrievedActivityType?.name)
            assertEquals(123, retrievedActivityType?.iconRes)
        }

    @Test
    fun testGetActivityIdByName() =
        runBlocking {
            val activityType =
                ActivityType(
                    id = 1,
                    name = "Work",
                    icon = 123,
                    color = 0xFF607D8B.toInt(),
                )

            val id = activityTypeDao.getActivityTypeByName(activityType.name).id

            assertNotNull(id)
        }

    @Test
    fun testInsertAndReadFocusSession() =
        runBlocking {
            val activityType =
                ActivityTypeEntity(
                    name = "Study",
                    iconRes = 456,
                    color = 0xFF607D8B.toInt(),
                )
            val activityId = activityTypeDao.insert(activityType)

            val startTime = LocalDateTime.now().minusHours(1)
            val endTime = LocalDateTime.now()
            val duration = Duration.between(startTime, endTime)

            val focusSession =
                FocusSessionEntity(
                    activityId = activityId,
                    startTime = startTime,
                    endTime = endTime,
                    duration = duration,
                    reflectionScore = 4,
                    reflectionNote = listOf("Great session"),
                )
            val sessionId = focusSessionDao.insert(focusSession)

            val retrievedSession = focusSessionDao.getSessionById(sessionId)

            assertNotNull(retrievedSession)
            assertEquals(activityId, retrievedSession?.activityId)
            assertEquals(4, retrievedSession?.reflectionScore)
        }

    @Test
    fun testGetSessionsByActivityId() =
        runBlocking {
            val activityType =
                ActivityTypeEntity(
                    name = "Reading",
                    iconRes = 789,
                    color = 0xFF607D8B.toInt(),
                )
            val activityId = activityTypeDao.insert(activityType)

            val startTime1 = LocalDateTime.now().minusDays(1)
            val endTime1 = startTime1.plusHours(2)
            val duration1 = Duration.between(startTime1, endTime1)

            val focusSession1 =
                FocusSessionEntity(
                    activityId = activityId,
                    startTime = startTime1,
                    endTime = endTime1,
                    duration = duration1,
                    reflectionScore = 3,
                    reflectionNote = listOf("Great session"),
                )

            val startTime2 = LocalDateTime.now().minusHours(5)
            val endTime2 = startTime2.plusHours(1)
            val duration2 = Duration.between(startTime2, endTime2)

            val focusSession2 =
                FocusSessionEntity(
                    activityId = activityId,
                    startTime = startTime2,
                    endTime = endTime2,
                    duration = duration2,
                    reflectionScore = 5,
                    reflectionNote = listOf("Great session"),
                )

            focusSessionDao.insert(focusSession1)
            focusSessionDao.insert(focusSession2)

            val sessions = focusSessionDao.getSessionsByActivityId(activityId).first()

            assertEquals(2, sessions.size)
        }
}
