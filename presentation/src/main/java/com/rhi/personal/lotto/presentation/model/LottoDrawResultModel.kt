package com.rhi.personal.lotto.presentation.model

import android.os.Parcelable
import com.rhi.personal.lotto.domain.model.LottoDrawResult
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat
import java.util.Date

@Parcelize
data class LottoDrawResultModel(
    val drawRound: Int,
    val drawDate: Date,
    val totalSellAmount: Long,
    val firstPrizeAmount: Long,
    val firstPrizeTotalAmount: Long,
    val firstPrizeWinnerCount: Int,
    val numbers: List<Int>,
    val bonusNumber: Int
): Parcelable {
    private val prizeFormat = DecimalFormat("#,###")

    fun getPrizeFormat(prize: Long): String {
        return prizeFormat.format(prize)
    }
}

fun LottoDrawResult.toModel(): LottoDrawResultModel = LottoDrawResultModel(
    drawRound = drawRound,
    drawDate = Date(drawDate),
    totalSellAmount = totalSellAmount,
    firstPrizeAmount = firstPrizeAmount,
    firstPrizeTotalAmount = firstPrizeTotalAmount,
    firstPrizeWinnerCount = firstPrizeWinnerCount,
    numbers = numbers,
    bonusNumber = bonusNumber
)