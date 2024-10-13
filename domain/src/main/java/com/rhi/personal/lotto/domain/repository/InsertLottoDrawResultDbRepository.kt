package com.rhi.personal.lotto.domain.repository

import com.rhi.personal.lotto.domain.model.LottoDrawResult

interface InsertLottoDrawResultDbRepository {
    suspend operator fun invoke(result: LottoDrawResult): Result<Unit>
}