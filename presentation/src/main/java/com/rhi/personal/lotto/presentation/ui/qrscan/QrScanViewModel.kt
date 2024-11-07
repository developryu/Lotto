package com.rhi.personal.lotto.presentation.ui.qrscan

import android.content.Context
import com.rhi.personal.lotto.presentation.R
import androidx.lifecycle.ViewModel
import com.rhi.personal.lotto.domain.usecase.LottoUseCase
import com.rhi.personal.lotto.domain.usecase.QrCodeScanUseCase
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import com.rhi.personal.lotto.presentation.model.LottoQrScanResultModel
import com.rhi.personal.lotto.presentation.model.MyNumberModel
import com.rhi.personal.lotto.presentation.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class QrScanViewModel @Inject constructor(
    private val context: Context,
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

            val rankAll = getRankAll(lottoDrawResult.toModel(), scanResult.myNumbers)
            val title1 = if (rankAll.isEmpty()) context.getString(R.string.qr_scan_result_rank_title1_1) else context.getString(R.string.qr_scan_result_rank_title1_2)
            val title2 = if (rankAll.isEmpty()) context.getString(R.string.qr_scan_result_rank_title2_1)  else String.format(context.getString(R.string.qr_scan_result_rank_title2_2), rankAll.joinToString(", "))
            val myNumbers: List<MyNumberModel> = scanResult.myNumbers.mapIndexed { index, number ->
                val rank = getRank(lottoDrawResult.toModel(), number)
                val title = if (rank == null) context.getString(R.string.qr_scan_result_rank1) else String.format(context.getString(R.string.qr_scan_result_rank2), rank)
                val order = 'A' + index
                MyNumberModel(number, title, order.toString())
            }


            reduce {
                state.copy(
                    qrScanResult = LottoQrScanResultModel(lottoDrawResult.toModel(), scanResult.url, myNumbers, title1, title2),
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

    private fun getRankAll(drawResult: LottoDrawResultModel, myNumbers: List<List<Int>>): List<Int> {
        val ranks = mutableListOf<Int>()
        myNumbers.forEach { myNumber ->
            val rank = getRank(drawResult, myNumber)
            if (rank != null) {
                ranks.add(rank)
            }
        }

        return ranks.distinct().sorted()
    }

    private fun getRank(drawResult: LottoDrawResultModel, myNumber: List<Int>) : Int? {
        val matchingCount = drawResult.numbers.intersect(myNumber).size
        return when (matchingCount) {
            6 -> 1
            5 -> if (myNumber.contains(drawResult.bonusNumber)) 2 else 3
            4 -> 4
            3 -> 5
            else -> null
        }
    }
}

data class QrScanState(
    val isQrScanSuccess: Boolean = false,
    val qrScanResult: LottoQrScanResultModel? = null,
)

sealed class QrScanSideEffect {
    data object ShowQrScanResultScreen: QrScanSideEffect()
}