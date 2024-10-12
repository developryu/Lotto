package com.rhi.personal.lotto.domain.usecase

import com.rhi.personal.lotto.domain.model.LottoDrawResult

interface LottoUseCase {
    suspend fun getLatestLottoDrawResult(): Result<LottoDrawResult>

    suspend fun getLatestLottoDrawRound(): Result<Int>

    suspend fun getLottoDrawResult(round: Int): Result<LottoDrawResult>

    suspend fun getLottoDrawResultList(latestRound: Int, count: Int): Result<List<LottoDrawResult>>
}