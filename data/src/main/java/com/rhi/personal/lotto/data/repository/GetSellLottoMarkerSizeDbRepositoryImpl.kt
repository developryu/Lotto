package com.rhi.personal.lotto.data.repository

import com.rhi.personal.lotto.data.source.room.dao.SellLottoMarkerDao
import com.rhi.personal.lotto.domain.repository.GetSellLottoMarkerSizeDbRepository
import javax.inject.Inject

class GetSellLottoMarkerSizeDbRepositoryImpl @Inject constructor(
    private val sellLottoMarkerDao: SellLottoMarkerDao
) : GetSellLottoMarkerSizeDbRepository{
    override suspend fun invoke(updateDate: Int): Result<Int> = runCatching {
        sellLottoMarkerDao.getSellLottoMarkerSize(updateDate)
    }
}