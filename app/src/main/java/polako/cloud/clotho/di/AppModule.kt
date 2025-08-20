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
import polako.cloud.clotho.domain.ActivityManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }
    
    @Provides
    @Singleton
    fun provideActivityManager(): ActivityManager {
        return ActivityManager()
    }
}