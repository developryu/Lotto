package com.rhi.personal.lotto.data.di

import com.rhi.personal.lotto.data.usecase.PermissionUseCaseImpl
import com.rhi.personal.lotto.domain.usecase.PermissionUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PermissionModule {
    @Binds
    fun bindPermissionUseCase(permissionUseCaseImpl: PermissionUseCaseImpl): PermissionUseCase
}