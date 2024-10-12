package com.rhi.personal.lotto.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SplashPreLoadModel(
    val latestLottoDrawResult: LottoDrawResultModel,
    val beforeLottoDrawResultList: List<LottoDrawResultModel>
): Parcelable
