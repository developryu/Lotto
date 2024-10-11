package com.rhi.personal.lotto.domain.repository

import com.rhi.personal.lotto.domain.model.LottoDrawResult

interface GetLottoResultApiRepository {
    suspend operator fun invoke(round: Int): Result<LottoDrawResult>
}