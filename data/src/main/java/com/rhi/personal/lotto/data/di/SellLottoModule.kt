package com.rhi.personal.lotto.data.di

import com.rhi.personal.lotto.data.repository.GetSellLottoMarkerDbRepositoryImpl
import com.rhi.personal.lotto.data.repository.GetSellLottoMarkerSizeDbRepositoryImpl
import com.rhi.personal.lotto.data.repository.InsertSellLottoMarkerDbRepositoryImpl
import com.rhi.personal.lotto.data.usecase.SellLottoMarkerUseCaseImpl
import com.rhi.personal.lotto.domain.repository.GetSellLottoMarkerDbRepository
import com.rhi.personal.lotto.domain.repository.GetSellLottoMarkerSizeDbRepository
import com.rhi.personal.lotto.domain.repository.InsertSellLottoMarkerDbRepository
import com.rhi.personal.lotto.domain.usecase.SellLottoMarkerUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SellLottoModule {

    @Binds
    fun bindInsertSellLottoMarkerDbRepository(insertSellLottoMarkerDbRepositoryImpl: InsertSellLottoMarkerDbRepositoryImpl): InsertSellLottoMarkerDbRepository

    @Binds
    fun bindGetSellLottoMarkerSizeDbRepository(getSellLottoMarkerSizeDbRepositoryImpl: GetSellLottoMarkerSizeDbRepositoryImpl): GetSellLottoMarkerSizeDbRepository

    @Binds
    fun bindGetSellLottoMarkerDbRepository(getSellLottoMarkerDbRepositoryImpl: GetSellLottoMarkerDbRepositoryImpl): GetSellLottoMarkerDbRepository

    @Binds
    fun bindSellLottoMarkerUseCase(sellLottoMarkerUseCaseImpl: SellLottoMarkerUseCaseImpl): SellLottoMarkerUseCase


}