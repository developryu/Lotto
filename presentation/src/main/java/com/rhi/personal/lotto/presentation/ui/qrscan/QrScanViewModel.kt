package com.rhi.personal.lotto.presentation.ui.qrscan

import androidx.lifecycle.ViewModel
import com.rhi.personal.lotto.domain.usecase.LottoUseCase
import com.rhi.personal.lotto.domain.usecase.QrCodeScanUseCase
import com.rhi.personal.lotto.presentation.model.LottoQrScanResultModel
import com.rhi.personal.lotto.presentation.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class QrScanViewModel @Inject constructor(
    private val qrCodeScanUseCase: QrCodeScanUseCase,
    private val lottoUseCase: LottoUseCase
) : ViewModel(), ContainerHost<QrScanState, QrScanSideEffect>{
    override val container: Container<QrScanState, QrScanSideEffect> = container(
        initialState = QrScanState()
    )
    private var scanString: String? = null

    fun scanQrCode(value: String) = intent {
        if (scanString == value) {
            return@intent
        }
        try {
            scanString = value
            val scanResult = qrCodeScanUseCase.scanQrCodeFromLottoUrl(value).getOrThrow()
            val lottoDrawResult = lottoUseCase.getLottoDrawResult(scanResult.drawRound).getOrThrow()
            reduce {
                state.copy(
                    qrScanResult = LottoQrScanResultModel(lottoDrawResult.toModel(), scanResult.url, scanResult.myNumbers),
                    isQrScanSuccess = true
                )
            }

            postSideEffect(QrScanSideEffect.ShowQrScanResultScreen)
        } catch (e: Exception) {
            reScanQrCode()
        }
    }

    fun reScanQrCode() = intent {
        scanString = null
        reduce {
            state.copy(
                qrScanResult = null,
                isQrScanSuccess = false
            )
        }
    }
}

data class QrScanState(
    val isQrScanSuccess: Boolean = false,
    val qrScanResult: LottoQrScanResultModel? = null
)

sealed class QrScanSideEffect {
    data object ShowQrScanResultScreen: QrScanSideEffect()
}