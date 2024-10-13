package com.rhi.personal.lotto.data.repository

import com.rhi.personal.lotto.data.source.room.dao.LottoDrawResultDao
import com.rhi.personal.lotto.domain.model.LottoDrawResult
import com.rhi.personal.lotto.domain.repository.GetLottoDrawResultListDbRepository
import javax.inject.Inject

class GetLottoDrawResultListDbRepositoryImpl @Inject constructor(
    private val lottoDrawResultDao: LottoDrawResultDao
) : GetLottoDrawResultListDbRepository {
    override suspend fun invoke(roundList: List<Int>): Result<List<LottoDrawResult>> = runCatching {
        lottoDrawResultDao.getLottoDrawResultList(roundList).map { it.toDomain() }
    }
}