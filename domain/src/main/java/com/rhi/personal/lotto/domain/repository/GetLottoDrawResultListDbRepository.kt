package com.rhi.personal.lotto.domain.repository

import com.rhi.personal.lotto.domain.model.LottoDrawResult

interface GetLottoDrawResultListDbRepository {
    suspend operator fun invoke(roundList: List<Int>): Result<List<LottoDrawResult>>
}