package com.rhi.personal.lotto.data.source.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.rhi.personal.lotto.data.source.room.converter.LottoDrawResultConverter
import com.rhi.personal.lotto.domain.model.LottoDrawResult
import java.util.Date

@Entity(tableName = "lotto_draw_result")
@TypeConverters(LottoDrawResultConverter::class)
data class LottoDrawResultEntity(
    @PrimaryKey(autoGenerate = false)
    val drawRound: Int,
    val drawDate: Date,
    val totalSellAmount: Long,
    val firstPrizeAmount: Long,
    val firstPrizeTotalAmount: Long,
    val firstPrizeWinnerCount: Int,
    val numbers: List<Int>,
    val bonusNumber: Int,
) {
    fun toDomain(): LottoDrawResult = LottoDrawResult(
        drawRound = drawRound,
        drawDate = drawDate.time,
        totalSellAmount = totalSellAmount,
        firstPrizeAmount = firstPrizeAmount,
        firstPrizeTotalAmount = firstPrizeTotalAmount,
        firstPrizeWinnerCount = firstPrizeWinnerCount,
        numbers = numbers,
        bonusNumber = bonusNumber,
    )
}

fun LottoDrawResult.toEntity(): LottoDrawResultEntity = LottoDrawResultEntity(
    drawRound = drawRound,
    drawDate = Date(drawDate),
    totalSellAmount = totalSellAmount,
    firstPrizeAmount = firstPrizeAmount,
    firstPrizeTotalAmount = firstPrizeTotalAmount,
    firstPrizeWinnerCount = firstPrizeWinnerCount,
    numbers = numbers,
    bonusNumber = bonusNumber
)
