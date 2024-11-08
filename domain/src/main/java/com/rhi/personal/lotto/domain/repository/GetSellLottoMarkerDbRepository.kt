package com.rhi.personal.lotto.domain.repository

import com.rhi.personal.lotto.domain.model.SellLottoMarker

interface GetSellLottoMarkerDbRepository {
    suspend fun getSellLottoMarkerAll(): Result<List<SellLottoMarker>>

    suspend fun getSellLottoMarkerBounds(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double,
    ): Result<List<SellLottoMarker>>
}