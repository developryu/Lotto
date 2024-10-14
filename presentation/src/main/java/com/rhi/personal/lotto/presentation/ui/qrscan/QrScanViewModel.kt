package com.rhi.personal.lotto.presentation.ui.qrscan

import android.util.Log
import androidx.lifecycle.ViewModel
import com.rhi.personal.lotto.domain.usecase.QrCodeScanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class QrScanViewModel @Inject constructor(
    private val qrCodeScanUseCase: QrCodeScanUseCase
) : ViewModel(), ContainerHost<QrScanState, QrScanSideEffect>{
    override val container: Container<QrScanState, QrScanSideEffect> = container(
        initialState = QrScanState()
    )

    fun scanQrCode(value: String) = intent {
        val data = qrCodeScanUseCase.scanQrCodeFromLottoUrl(value).getOrThrow()
        Log.e("test", "scanQrCode: $data")
    }
}

data class QrScanState(
    val url: String? = null
)

sealed class QrScanSideEffect