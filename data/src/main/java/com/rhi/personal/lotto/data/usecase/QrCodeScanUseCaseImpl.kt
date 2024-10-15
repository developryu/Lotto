package com.rhi.personal.lotto.data.usecase

import com.rhi.personal.lotto.domain.model.LottoQrScanResult
import com.rhi.personal.lotto.domain.usecase.QrCodeScanUseCase
import javax.inject.Inject

class QrCodeScanUseCaseImpl @Inject constructor() : QrCodeScanUseCase {

    override suspend fun scanQrCodeFromLottoUrl(url: String): Result<LottoQrScanResult> = runCatching {
        val roundRegex = Regex("v=(\\d+)")
        val roundMatch = roundRegex.find(url)

        val numbersRegex = Regex("q(\\d{12})")
        val numbersMatches = numbersRegex.findAll(url)


        if (url.contains("m.dhlottery.co.kr")
            && roundMatch != null
            && numbersMatches.any()) {
            val roundNumber = roundMatch.groupValues[1].toInt()
            val lottoNumbers = numbersMatches.map { match ->
                match.groupValues[1].chunked(2).map { it.toInt() }
            }.toList()
            LottoQrScanResult(url, roundNumber, lottoNumbers)
        } else {
            throw IllegalArgumentException("The URL format is incorrect.")
        }
    }

}