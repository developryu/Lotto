package com.rhi.personal.lotto.data.repository

import com.rhi.personal.lotto.data.source.retrofit.LottoService
import com.rhi.personal.lotto.domain.model.LottoDrawResult
import com.rhi.personal.lotto.domain.repository.GetLottoResultApiRepository
import javax.inject.Inject

class GetLottoResultApiRepositoryImpl @Inject constructor(
    private val lottoService: LottoService
) : GetLottoResultApiRepository {
    override suspend fun invoke(round: Int): Result<LottoDrawResult> = runCatching {
        val result = lottoService.getLottoDrawResult(round)
        if (result.returnValue == "fail") {
            throw Exception("Fail to get lotto result")
        } else {
            result.toDomain()
        }
    }
}