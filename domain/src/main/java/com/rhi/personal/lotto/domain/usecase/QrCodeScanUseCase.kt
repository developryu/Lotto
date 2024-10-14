package com.rhi.personal.lotto.domain.usecase

import com.rhi.personal.lotto.domain.model.LottoQrCodeScanResult

interface QrCodeScanUseCase {

    suspend fun scanQrCodeFromLottoUrl(url: String): Result<LottoQrCodeScanResult?>

}