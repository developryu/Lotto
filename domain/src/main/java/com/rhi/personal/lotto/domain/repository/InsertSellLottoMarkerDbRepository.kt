package com.rhi.personal.lotto.domain.repository

import com.rhi.personal.lotto.domain.model.SellLottoMarker

interface InsertSellLottoMarkerDbRepository {
    suspend operator fun invoke(markets: List<SellLottoMarker>): Result<Unit>
}