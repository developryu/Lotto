package com.rhi.personal.lotto.presentation.ui.main.home

import androidx.lifecycle.ViewModel
import com.rhi.personal.lotto.domain.usecase.LottoUseCase
import com.rhi.personal.lotto.presentation.constant.MORE_LOAD_SIZE
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import com.rhi.personal.lotto.presentation.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val lottoUseCase: LottoUseCase
) : ViewModel(), ContainerHost<HomeState, HomeSideEffect> {
    override val container: Container<HomeState, HomeSideEffect> = container(
        initialState = HomeState()
    )

    fun setInitData(
        latestLottoDrawResult: LottoDrawResultModel?,
        beforeLottoDrawResultList: List<LottoDrawResultModel>?
    ) = intent {
        if (
            state.latestLottoDrawResult == null
            && state.beforeLottoDrawResultList == null
            && latestLottoDrawResult != null
            && beforeLottoDrawResultList != null
            ) {
            reduce {
                state.copy(
                    latestLottoDrawResult = latestLottoDrawResult,
                    beforeLottoDrawResultList = beforeLottoDrawResultList
                )
            }
        }
    }

    fun onMoreLoadClick(latestIndex: Int) = intent {
        MORE_LOAD_SIZE
        val result = lottoUseCase.getLottoDrawResultList(latestIndex, MORE_LOAD_SIZE).getOrThrow()
        reduce {
            state.copy(
                beforeLottoDrawResultList = state.beforeLottoDrawResultList?.plus(result.map { it.toModel() })
            )
        }
    }
}

data class HomeState(
    val latestLottoDrawResult: LottoDrawResultModel? = null,
    val beforeLottoDrawResultList: List<LottoDrawResultModel>? = null
)

sealed class HomeSideEffect {

}