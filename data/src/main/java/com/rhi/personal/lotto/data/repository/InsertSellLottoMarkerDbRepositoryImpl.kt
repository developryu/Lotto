package com.rhi.personal.lotto.data.repository

import com.rhi.personal.lotto.data.source.room.dao.SellLottoMarkerDao
import com.rhi.personal.lotto.data.source.room.entity.toEntity
import com.rhi.personal.lotto.domain.model.SellLottoMarker
import com.rhi.personal.lotto.domain.repository.InsertSellLottoMarkerDbRepository
import javax.inject.Inject

class InsertSellLottoMarkerDbRepositoryImpl @Inject constructor(
    private val sellLottoMarkerDao: SellLottoMarkerDao
) : InsertSellLottoMarkerDbRepository{

    override suspend fun invoke(markets: List<SellLottoMarker>): Result<Unit> = runCatching {
        sellLottoMarkerDao.insertSellLottoMarker(markets.map { it.toEntity() })
    }

}