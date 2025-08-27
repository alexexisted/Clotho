package polako.cloud.clotho.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import polako.cloud.clotho.R
import polako.cloud.clotho.data.local.AppDatabase
import polako.cloud.clotho.data.local.dao.ActivityTypeDao
import polako.cloud.clotho.data.local.dao.FocusSessionDao
import polako.cloud.clotho.data.local.entity.ActivityTypeEntity
import polako.cloud.clotho.data.repository.ActivityTypeRepository
import polako.cloud.clotho.data.repository.FocusSessionRepository
import polako.cloud.clotho.domain.repositoryImpl.ActivityTypeRepositoryImpl
import polako.cloud.clotho.domain.repositoryImpl.FocusSessionRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "clotho_database",
            ).addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        val database =
                            Room
                                .databaseBuilder(
                                    context,
                                    AppDatabase::class.java,
                                    "clotho_database",
                                ).build()

                        CoroutineScope(Dispatchers.IO).launch {
                            val activityTypes =
                                listOf(
                                    ActivityTypeEntity(
                                        name = "Work",
                                        iconRes = R.drawable.icon_work,
                                        color = 0xFFFF7043.toInt(),
                                    ),
                                    ActivityTypeEntity(
                                        name = "Study",
                                        iconRes = R.drawable.icon_school,
                                        color = 0xFFFFC107.toInt(),
                                    ),
                                    ActivityTypeEntity(
                                        name = "Reading",
                                        iconRes = R.drawable.icon_book,
                                        color = 0xFF26A69A.toInt(),
                                    ),
                                    ActivityTypeEntity(
                                        name = "Meditation",
                                        iconRes = R.drawable.icon_mind,
                                        color = 0xFF29B6F6.toInt(),
                                    ),
                                    ActivityTypeEntity(
                                        name = "Sport",
                                        iconRes = R.drawable.icon_barbell,
                                        color = 0xFFCDDC39.toInt(),
                                    ),
                                    ActivityTypeEntity(
                                        name = "Other",
                                        iconRes = R.drawable.icon_other,
                                        color = 0xFF607D8B.toInt(),
                                    ),
                                )
                            activityTypes.forEach { activityType ->
                                database.activityTypeDao().insert(activityType)
                            }
                        }
                    }
                },
            ).build()

    @Provides
    @Singleton
    fun provideActivityTypeDao(database: AppDatabase): ActivityTypeDao = database.activityTypeDao()

    @Provides
    @Singleton
    fun provideFocusSessionDao(database: AppDatabase): FocusSessionDao = database.focusSessionDao()

    @Provides
    @Singleton
    fun provideActivityTypeRepository(activityTypeDao: ActivityTypeDao): ActivityTypeRepository =
        ActivityTypeRepositoryImpl(activityTypeDao)

    @Provides
    @Singleton
    fun provideFocusSessionRepository(
        focusSessionDao: FocusSessionDao,
        activityTypeDao: ActivityTypeDao,
    ): FocusSessionRepository = FocusSessionRepositoryImpl(focusSessionDao, activityTypeDao)
}
