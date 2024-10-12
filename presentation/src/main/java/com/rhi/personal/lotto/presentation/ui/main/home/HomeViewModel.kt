package com.rhi.personal.lotto.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container: Container<HomeState, HomeSideEffect> = container(
        initialState = HomeState()
    )

    fun setInitData(
        latestLottoDrawResult: LottoDrawResultModel?,
        beforeLottoDrawResultList: List<LottoDrawResultModel>?
    ) = intent {
        if (latestLottoDrawResult != null && beforeLottoDrawResultList != null) {
            reduce {
                state.copy(
                    latestLottoDrawResult = latestLottoDrawResult,
                    beforeLottoDrawResultList = beforeLottoDrawResultList
                )
            }
        }
    }
}

data class HomeState(
    val latestLottoDrawResult: LottoDrawResultModel? = null,
    val beforeLottoDrawResultList: List<LottoDrawResultModel>? = null
)

sealed class HomeSideEffect {

}