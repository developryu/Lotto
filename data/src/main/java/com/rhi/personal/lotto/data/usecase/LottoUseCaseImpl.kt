package com.rhi.personal.lotto.data.usecase

import android.util.Log
import com.rhi.personal.lotto.domain.model.LottoDrawResult
import com.rhi.personal.lotto.domain.repository.GetLottoDrawResultApiRepository
import com.rhi.personal.lotto.domain.repository.GetLottoDrawResultDbRepository
import com.rhi.personal.lotto.domain.repository.GetLottoDrawResultListDbRepository
import com.rhi.personal.lotto.domain.repository.InsertLottoDrawResultDbRepository
import com.rhi.personal.lotto.domain.usecase.LottoUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class LottoUseCaseImpl @Inject constructor(
    private val getLottoDrawResultApiRepository: GetLottoDrawResultApiRepository,
    private val insertLottoDrawResultDbRepository: InsertLottoDrawResultDbRepository,
    private val getLottoDrawResultDbRepository: GetLottoDrawResultDbRepository,
    private val getLottoDrawResultListDbRepository: GetLottoDrawResultListDbRepository
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
        getLottoDrawResultDbRepository(round).getOrNull() ?: run {
            getLottoDrawResultApiRepository(round).getOrThrow().also { result ->
                insertLottoDrawResultDbRepository(result)
            }
        }
    }

    override suspend fun getLottoDrawResultList(
        latestRound: Int,
        count: Int
    ): Result<List<LottoDrawResult>> = runCatching {
        val roundList = (latestRound downTo latestRound - count + 1).toList()
        val dbList = getLottoDrawResultListDbRepository(roundList).getOrThrow()
        if (dbList.size == count) {
            dbList
        } else {
            coroutineScope {
                val searchRoundList = dbList.map { it.drawRound }
                val lossRoundList = roundList.filterNot { it in searchRoundList }
                val lossList = lossRoundList.map { round ->
                    async { this@LottoUseCaseImpl.getLottoDrawResult(round).getOrNull() }
                }.awaitAll().filterNotNull()
                (dbList + lossList).toList().sortedByDescending { it.drawRound }
            }
        }
    }
}