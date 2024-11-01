package com.rhi.personal.lotto.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LottoQrScanResultModel(
    val drawResult: LottoDrawResultModel,
    val url: String,
    val myNumber: List<MyNumberModel>,
    val resultTitle1: String,
    val resultTitle2: String
): Parcelable

@Parcelize
data class MyNumberModel(
    val numbers: List<Int>,
    val resultTitle: String,
    val order: String
): Parcelable