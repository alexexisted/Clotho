package polako.cloud.clotho.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import polako.cloud.clotho.data.local.dao.ActivityTypeDao
import polako.cloud.clotho.data.local.dao.FocusSessionDao
import polako.cloud.clotho.data.local.entity.ActivityTypeEntity
import polako.cloud.clotho.data.local.entity.FocusSessionEntity
import polako.cloud.clotho.data.local.util.Converters

@Database(
    entities = [
        ActivityTypeEntity::class,
        FocusSessionEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityTypeDao(): ActivityTypeDao

    abstract fun focusSessionDao(): FocusSessionDao
}
