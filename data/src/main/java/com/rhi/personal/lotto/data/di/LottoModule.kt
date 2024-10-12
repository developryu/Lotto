package com.rhi.personal.lotto.data.di

import com.rhi.personal.lotto.data.repository.GetLottoResultApiRepositoryImpl
import com.rhi.personal.lotto.data.usecase.LottoUseCaseImpl
import com.rhi.personal.lotto.domain.repository.GetLottoResultApiRepository
import com.rhi.personal.lotto.domain.usecase.LottoUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LottoModule {
    // Repository
    @Binds
    fun bindGetLottoResultApiRepository(getLottoResultApiRepositoryImpl: GetLottoResultApiRepositoryImpl): GetLottoResultApiRepository

    // UseCase
    @Binds
    fun bindLottoUseCase(lottoUseCaseImpl: LottoUseCaseImpl): LottoUseCase
}