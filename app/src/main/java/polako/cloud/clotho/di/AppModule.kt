package polako.cloud.clotho.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import polako.cloud.clotho.data.repository.FocusTimerRepository
import polako.cloud.clotho.domain.repositoryImpl.FocusTimerRepositoryImpl
import polako.cloud.clotho.service.ActivityManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(
        @ApplicationContext context: Context,
    ): Context = context

    @Provides
    @Singleton
    fun provideActivityManager(): ActivityManager = ActivityManager()

    @Provides
    @Singleton
    fun provideFocusTimerRepository(impl: FocusTimerRepositoryImpl): FocusTimerRepository = impl
}
