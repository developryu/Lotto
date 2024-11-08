package com.rhi.personal.lotto.data.usecase

import com.rhi.personal.lotto.domain.model.SellLottoMarker
import com.rhi.personal.lotto.domain.repository.GetSellLottoMarkerDbRepository
import com.rhi.personal.lotto.domain.repository.GetSellLottoMarkerSizeDbRepository
import com.rhi.personal.lotto.domain.repository.InsertSellLottoMarkerDbRepository
import com.rhi.personal.lotto.domain.usecase.SellLottoMarkerUseCase
import javax.inject.Inject

class SellLottoMarkerUseCaseImpl @Inject  constructor(
    private val insertSellLottoMarkerDbRepository: InsertSellLottoMarkerDbRepository,
    private val getSellLottoMarkerSizeDbRepository: GetSellLottoMarkerSizeDbRepository,
    private val getSellLottoMarkerDbRepository: GetSellLottoMarkerDbRepository
) : SellLottoMarkerUseCase{
    override suspend fun getSellLottoMarkerAll(): Result<List<SellLottoMarker>> = runCatching {
        getSellLottoMarkerDbRepository.getSellLottoMarkerAll().getOrThrow()
    }

    override suspend fun getSellLottoMarkerBounds(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double,
    ): Result<List<SellLottoMarker>> = runCatching {
        getSellLottoMarkerDbRepository.getSellLottoMarkerBounds(startLatitude, startLongitude, endLatitude, endLongitude).getOrThrow()
    }

    override suspend fun getSellLottoMarkerSize(updateDate: Int): Result<Int> = runCatching {
        getSellLottoMarkerSizeDbRepository(updateDate).getOrThrow()
    }

    override suspend fun insertSellLottoMarker(markets: List<SellLottoMarker>): Result<Unit> = runCatching {
        insertSellLottoMarkerDbRepository(markets).getOrThrow()
    }

}