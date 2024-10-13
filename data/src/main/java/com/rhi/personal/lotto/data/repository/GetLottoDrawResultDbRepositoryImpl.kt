package com.rhi.personal.lotto.data.repository

import com.rhi.personal.lotto.data.source.room.dao.LottoDrawResultDao
import com.rhi.personal.lotto.domain.model.LottoDrawResult
import com.rhi.personal.lotto.domain.repository.GetLottoDrawResultDbRepository
import javax.inject.Inject

class GetLottoDrawResultDbRepositoryImpl @Inject constructor(
    private val lottoDrawResultDao: LottoDrawResultDao
) : GetLottoDrawResultDbRepository {
    override suspend fun invoke(round: Int): Result<LottoDrawResult?> = runCatching {
        lottoDrawResultDao.getLottoDrawResult(round)?.toDomain()
    }
}