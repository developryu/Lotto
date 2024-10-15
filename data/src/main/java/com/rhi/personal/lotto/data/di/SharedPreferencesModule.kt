package com.rhi.personal.lotto.data.di

import com.rhi.personal.lotto.data.repository.SharedPreferencesRepositoryImpl
import com.rhi.personal.lotto.domain.repository.SharedPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SharedPreferencesModule {
    @Binds
    fun bindSharedPreferencesRepository(sharedPreferencesRepositoryImpl: SharedPreferencesRepositoryImpl): SharedPreferencesRepository
}