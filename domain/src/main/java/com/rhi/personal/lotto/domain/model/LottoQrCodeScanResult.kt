package com.rhi.personal.lotto.domain.model

data class LottoQrCodeScanResult(
    val url: String,
    val drawRound: Int,
    val myNumbers: List<List<Int>>
)