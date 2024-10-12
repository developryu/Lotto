package com.rhi.personal.lotto.data.usecase

import com.rhi.personal.lotto.domain.model.LottoDrawResult
import com.rhi.personal.lotto.domain.repository.GetLottoResultApiRepository
import com.rhi.personal.lotto.domain.usecase.LottoUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class LottoUseCaseImpl @Inject constructor(
    private val getLottoResultApiRepository: GetLottoResultApiRepository
) : LottoUseCase {
    override suspend fun getLatestLottoDrawResult(): Result<LottoDrawResult> = runCatching {
        val latestRound = this@LottoUseCaseImpl.getLatestLottoDrawRound().getOrThrow()
        this@LottoUseCaseImpl.getLottoDrawResult(latestRound).getOrThrow()
    }

    override suspend fun getLatestLottoDrawRound(): Result<Int> = runCatching {
        val firstLottoDate = LocalDate.of(2002, 12, 7)
        val today = LocalDate.now()
        val weeksBetween = ChronoUnit.WEEKS.between(firstLottoDate, today)
        var latestDrawNumber = weeksBetween + 1
        if (today.dayOfWeek.name == "SATURDAY" && LocalTime.now().isBefore(LocalTime.of(20, 50))) {
            latestDrawNumber -= 1
        }
        latestDrawNumber.toInt()
    }

    override suspend fun getLottoDrawResult(round: Int): Result<LottoDrawResult> = runCatching {
        getLottoResultApiRepository(round).getOrThrow()
    }

    override suspend fun getLottoDrawResultList(
        latestRound: Int,
        count: Int
    ): Result<List<LottoDrawResult>> = runCatching {
        coroutineScope {
            (latestRound downTo latestRound - count).map { round ->
                async { this@LottoUseCaseImpl.getLottoDrawResult(round).getOrNull() }
            }.awaitAll().filterNotNull()
        }
    }
}