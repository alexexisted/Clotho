package polako.cloud.clotho.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import polako.cloud.clotho.data.local.AppDatabase
import polako.cloud.clotho.data.local.dao.ActivityTypeDao
import polako.cloud.clotho.data.local.dao.FocusSessionDao
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "clotho_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideActivityTypeDao(database: AppDatabase): ActivityTypeDao {
        return database.activityTypeDao()
    }

    @Provides
    @Singleton
    fun provideFocusSessionDao(database: AppDatabase): FocusSessionDao {
        return database.focusSessionDao()
    }

    @Provides
    @Singleton
    fun provideActivityTypeRepository(activityTypeDao: ActivityTypeDao): ActivityTypeRepository {
        return ActivityTypeRepositoryImpl(activityTypeDao)
    }

    @Provides
    @Singleton
    fun provideFocusSessionRepository(
        focusSessionDao: FocusSessionDao,
        activityTypeDao: ActivityTypeDao
    ): FocusSessionRepository {
        return FocusSessionRepositoryImpl(focusSessionDao, activityTypeDao)
    }
}