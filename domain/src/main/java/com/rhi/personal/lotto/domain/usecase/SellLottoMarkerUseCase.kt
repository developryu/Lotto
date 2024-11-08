package com.rhi.personal.lotto.domain.usecase

import com.rhi.personal.lotto.domain.model.SellLottoMarker

interface SellLottoMarkerUseCase {

    suspend fun getSellLottoMarkerAll(): Result<List<SellLottoMarker>>

    suspend fun getSellLottoMarkerBounds(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double,
    ): Result<List<SellLottoMarker>>

    suspend fun getSellLottoMarkerSize(updateDate: Int): Result<Int>

    suspend fun insertSellLottoMarker(markets: List<SellLottoMarker>): Result<Unit>
}