package com.rhi.personal.lotto.data.repository

import com.rhi.personal.lotto.data.source.room.dao.LottoDrawResultDao
import com.rhi.personal.lotto.data.source.room.entity.toEntity
import com.rhi.personal.lotto.domain.model.LottoDrawResult
import com.rhi.personal.lotto.domain.repository.InsertLottoDrawResultDbRepository
import javax.inject.Inject

class InsertLottoDrawResultDbRepositoryImpl @Inject constructor(
    private val lottoDrawResultDao: LottoDrawResultDao
) : InsertLottoDrawResultDbRepository {
    override suspend fun invoke(result: LottoDrawResult): Result<Unit> = runCatching {
        lottoDrawResultDao.insertLottoDrawResult(result.toEntity())
    }
}