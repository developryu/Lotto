package com.rhi.personal.lotto.domain.repository

interface GetSellLottoMarkerSizeDbRepository {
    suspend operator fun invoke(updateDate: Int): Result<Int>
}