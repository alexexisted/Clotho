package polako.cloud.clotho.data.local.dao

import androidx.room.*
import polako.cloud.clotho.data.local.entity.ActivityTypeEntity

@Dao
interface ActivityTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activityType: ActivityTypeEntity): Long

    @Update
    suspend fun update(activityType: ActivityTypeEntity)

    @Delete
    suspend fun delete(activityType: ActivityTypeEntity)

    @Query("SELECT * FROM activity_type WHERE id = :id")
    suspend fun getActivityTypeById(id: Long): ActivityTypeEntity?

    @Query("SELECT * FROM activity_type WHERE name = :name")
    suspend fun getActivityTypeByName(name: String): ActivityTypeEntity

    @Query("SELECT * FROM activity_type")
    suspend fun getAllActivities(): List<ActivityTypeEntity>

    @Query("DELETE FROM activity_type")
    suspend fun deleteAllActivityTypes()
}
