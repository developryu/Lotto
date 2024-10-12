package com.rhi.personal.lotto.data.model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import com.rhi.personal.lotto.domain.model.LottoDrawResult
import java.text.SimpleDateFormat

data class LottoApiResponse(
    @SerializedName("returnValue")
    val returnValue: String,    // 요청결과: success, fail
    @SerializedName("drwNoDate")
    val drwNoDate: String,      // 날짜
    @SerializedName("drwNo")
    val drwNo: Int,             // 회차
    @SerializedName("totSellamnt")
    val totSellAmount: Long,      // 총판매금액
    @SerializedName("firstWinamnt")
    val firstWinAmount: Long,     // 1등 당첨금
    @SerializedName("firstPrzwnerCo")
    val firstPrzWinnerCount: Int,    // 1등 당첨인원
    @SerializedName("firstAccumamnt")
    val firstAccumAmount: Long,   // 1등 당첨금 총액
    @SerializedName("drwtNo1")
    val no1: Int,           // 당첨번호 1
    @SerializedName("drwtNo2")
    val no2: Int,           // 당첨번호 2
    @SerializedName("drwtNo3")
    val no3: Int,           // 당첨번호 3
    @SerializedName("drwtNo4")
    val no4: Int,           // 당첨번호 4
    @SerializedName("drwtNo5")
    val no5: Int,           // 당첨번호 5
    @SerializedName("drwtNo6")
    val no6: Int,           // 당첨번호 6
    @SerializedName("bnusNo")
    val bonusNo: Int,            // 보너스 번호
) {
    @SuppressLint("SimpleDateFormat")
    fun toDomain(): LottoDrawResult = LottoDrawResult(
        drawRound = drwNo,
        drawDate = SimpleDateFormat("yyyy-MM-dd").parse(drwNoDate)?.time ?: 0,
        totalSellAmount = totSellAmount,
        firstPrizeAmount = firstWinAmount,
        firstPrizeTotalAmount = firstAccumAmount,
        firstPrizeWinnerCount = firstPrzWinnerCount,
        numbers = listOf(no1, no2, no3, no4, no5, no6),
        bonusNumber = bonusNo,
    )
}
