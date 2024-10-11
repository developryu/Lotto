package com.rhi.personal.lotto.domain.usecase

import com.rhi.personal.lotto.domain.model.LottoDrawResult

interface LottoUseCase {
    suspend fun getLatestLottoResult(): Result<LottoDrawResult>

    suspend fun getLatestLottoRound(): Result<Int>

    suspend fun getLottoResult(round: Int): Result<LottoDrawResult>

    suspend fun getLottoResultList(latestRound: Int, count: Int): Result<List<LottoDrawResult>>
}