package com.rhi.personal.lotto.domain.usecase

import com.rhi.personal.lotto.domain.model.LottoQrScanResult

interface QrCodeScanUseCase {

    suspend fun scanQrCodeFromLottoUrl(url: String): Result<LottoQrScanResult>

}