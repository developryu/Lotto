package com.rhi.personal.lotto.presentation.ui.splash

import androidx.lifecycle.ViewModel
import com.rhi.personal.lotto.domain.usecase.LottoUseCase
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import com.rhi.personal.lotto.presentation.model.SplashPreLoadModel
import com.rhi.personal.lotto.presentation.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val lottoUseCase: LottoUseCase
) : ViewModel(), ContainerHost<SplashState, SplashSideEffect> {

    override val container: Container<SplashState, SplashSideEffect> = container(
        initialState = SplashState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    reduce { state.copy(isLoading = false) }
                    postSideEffect(SplashSideEffect.LoadDataFail(throwable))
                }
            }
        }
    )

    init {
        setPreLoadData()
    }


    @Suppress("UNCHECKED_CAST")
    private fun setPreLoadData() = intent {
        reduce { state.copy(isLoading = true) }

        val preLoadData = awaitAll(
            fetchLatestLottoResult(),
            fetchBeforeLottoResultList(),
            defaultDelay()
        )
        postSideEffect(
            SplashSideEffect.LoadDataSuccess(
                SplashPreLoadModel(
                    preLoadData[0] as LottoDrawResultModel,
                    preLoadData[1] as List<LottoDrawResultModel>
                )
            )
        )
        reduce { state.copy(isLoading = false) }
    }

    private suspend fun fetchLatestLottoResult() = coroutineScope {
        async {
            lottoUseCase.getLatestLottoDrawResult().getOrThrow().toModel()
        }
    }

    private suspend fun fetchBeforeLottoResultList() = coroutineScope {
        async {
            lottoUseCase.getLottoDrawResultList(lottoUseCase.getLatestLottoDrawRound().getOrThrow() - 1, 5).getOrThrow().map { it.toModel() }
        }
    }

    private suspend fun defaultDelay() = coroutineScope {
        async {
            delay(1500L)
        }
    }
}

data class SplashState(
    val isLoading: Boolean = false,
)

sealed class SplashSideEffect {
    data class LoadDataSuccess(val preLoadModel: SplashPreLoadModel) : SplashSideEffect()
    data class LoadDataFail(val error: Throwable) : SplashSideEffect()
}