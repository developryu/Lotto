package com.rhi.personal.lotto.data.di

import com.rhi.personal.lotto.data.repository.GetLottoDrawResultApiRepositoryImpl
import com.rhi.personal.lotto.data.repository.GetLottoDrawResultDbRepositoryImpl
import com.rhi.personal.lotto.data.repository.GetLottoDrawResultListDbRepositoryImpl
import com.rhi.personal.lotto.data.repository.InsertLottoDrawResultDbRepositoryImpl
import com.rhi.personal.lotto.data.usecase.LottoUseCaseImpl
import com.rhi.personal.lotto.domain.repository.GetLottoDrawResultApiRepository
import com.rhi.personal.lotto.domain.repository.GetLottoDrawResultDbRepository
import com.rhi.personal.lotto.domain.repository.GetLottoDrawResultListDbRepository
import com.rhi.personal.lotto.domain.repository.InsertLottoDrawResultDbRepository
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
    fun bindGetLottoDrawResultApiRepository(getLottoDrawResultApiRepositoryImpl: GetLottoDrawResultApiRepositoryImpl): GetLottoDrawResultApiRepository

    @Binds
    fun bindInsertLottoDrawResultDbRepository(insertLottoDrawResultDbRepositoryImpl: InsertLottoDrawResultDbRepositoryImpl): InsertLottoDrawResultDbRepository

    @Binds
    fun bindGetLottoDrawResultDbRepository(getLottoDrawResultDbRepositoryImpl: GetLottoDrawResultDbRepositoryImpl): GetLottoDrawResultDbRepository

    @Binds
    fun bindGetLottoDrawResultListDbRepository(getLottoDrawResultListDbRepositoryImpl: GetLottoDrawResultListDbRepositoryImpl): GetLottoDrawResultListDbRepository

    // UseCase
    @Binds
    fun bindLottoUseCase(lottoUseCaseImpl: LottoUseCaseImpl): LottoUseCase
}