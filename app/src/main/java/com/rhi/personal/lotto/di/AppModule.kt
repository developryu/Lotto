package com.rhi.personal.lotto.di

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.rhi.personal.lotto.BuildConfig
import com.rhi.personal.lotto.data.di.IsDebug
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindContext(application: Application): Context

    companion object {

        @Provides
        @IsDebug
        fun provideIsDebug(): Boolean {
            return BuildConfig.DEBUG
        }

        @Provides
        @Singleton
        fun provideWorkManager (
            @ApplicationContext context: Context
        ): WorkManager = WorkManager.getInstance(context)

        @Provides
        @Singleton
        fun provideWorkManagerConfiguration(
            workerFactory: HiltWorkerFactory
        ): Configuration = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}