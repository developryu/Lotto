package com.rhi.personal.lotto.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.rhi.personal.lotto.BuildConfig
import com.rhi.personal.lotto.data.di.IsDebug

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

    }
}