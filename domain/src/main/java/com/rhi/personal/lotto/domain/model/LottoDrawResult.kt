package com.rhi.personal.lotto.domain.model

data class LottoDrawResult(
    val drawRound: Int,
    val drawDate: Long,
    val totalSellAmount: Long,
    val firstPrizeAmount: Long,
    val firstPrizeTotalAmount: Long,
    val firstWinnerCount: Int,
    val numbers: List<Int>,
    val bonusNumber: Int
)
