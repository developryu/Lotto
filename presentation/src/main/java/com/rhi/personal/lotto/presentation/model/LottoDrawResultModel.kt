package com.rhi.personal.lotto.presentation.model

import android.icu.text.DateFormat
import android.os.Parcelable
import com.rhi.personal.lotto.domain.model.LottoDrawResult
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel
import java.text.DecimalFormat
import java.util.Date
import java.util.Locale

@Parcelize
data class LottoDrawResultModel(
    val drawRound: Int,
    val drawDate: Date,
    val totalSellAmount: Long,
    val firstPrizeAmount: Long,
    val firstPrizeTotalAmount: Long,
    val firstWinnerCount: Int,
    val numbers: List<Int>,
    val bonusNumber: Int
): Parcelable {
    @IgnoredOnParcel
    private val prizeFormat = DecimalFormat("#,###")

    fun getPrizeFormat(prize: Long): String {
        return prizeFormat.format(prize)
    }

    fun getDrawDateFormat(): String {
        val locale = Locale.getDefault()
        val dateFormat = DateFormat.getDateInstance(DateFormat.LONG, locale)
        return dateFormat.format(drawDate)
    }

    fun getDrawDateShortFormat(): String {
        val locale = Locale.getDefault()
        val dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale)
        return dateFormat.format(drawDate)
    }
}

fun LottoDrawResult.toModel(): LottoDrawResultModel = LottoDrawResultModel(
    drawRound = drawRound,
    drawDate = Date(drawDate),
    totalSellAmount = totalSellAmount,
    firstPrizeAmount = firstPrizeAmount,
    firstPrizeTotalAmount = firstPrizeTotalAmount,
    firstWinnerCount = firstWinnerCount,
    numbers = numbers,
    bonusNumber = bonusNumber
)