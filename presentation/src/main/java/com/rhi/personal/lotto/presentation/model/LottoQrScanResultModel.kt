package com.rhi.personal.lotto.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LottoQrScanResultModel(
    val drawResult: LottoDrawResultModel,
    val url: String,
    val myNumber: List<List<Int>>
): Parcelable
