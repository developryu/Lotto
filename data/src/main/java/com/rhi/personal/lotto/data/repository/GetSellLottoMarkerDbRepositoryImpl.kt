package com.rhi.personal.lotto.data.repository

import com.rhi.personal.lotto.data.source.room.dao.SellLottoMarkerDao
import com.rhi.personal.lotto.domain.model.SellLottoMarker
import com.rhi.personal.lotto.domain.repository.GetSellLottoMarkerDbRepository
import javax.inject.Inject

class GetSellLottoMarkerDbRepositoryImpl @Inject constructor(
    private val sellLottoMarkerDao: SellLottoMarkerDao
) : GetSellLottoMarkerDbRepository {
    override suspend fun getSellLottoMarkerAll(): Result<List<SellLottoMarker>> = runCatching {
        sellLottoMarkerDao.getSellLottoMarkerAll().map { it.toDomain() }
    }

    override suspend fun getSellLottoMarkerBounds(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double,
    ): Result<List<SellLottoMarker>> = runCatching {
        sellLottoMarkerDao.getSellLottoMarkerBounds(startLatitude, startLongitude, endLatitude, endLongitude).map { it.toDomain() }
    }
}