package com.rhi.personal.lotto.presentation.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.rhi.personal.lotto.domain.usecase.LottoUseCase
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import com.rhi.personal.lotto.presentation.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LottoResultHistoryViewModel @Inject constructor(
    private val lottoUseCase: LottoUseCase
) : ViewModel(), ContainerHost<LottoResultHistoryState, LottoResultHistorySideEffect> {
    override val container: Container<LottoResultHistoryState, LottoResultHistorySideEffect> = container(
        initialState = LottoResultHistoryState()
    )

    fun getLottoResultHistoryPage() = intent {
        val latestDrawRound: Int = lottoUseCase.getLatestLottoDrawRound().getOrThrow()
        val flow = Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                LottoResultHistoryPagingSource(
                    lottoUseCase = lottoUseCase,
                    latestDrawRound = latestDrawRound,
                    pageSize = 30
                )
            }
        ).flow.cachedIn(viewModelScope)
        reduce {
            state.copy(
                historyItemFlow = flow
            )
        }
    }
}


class LottoResultHistoryPagingSource @Inject constructor(
    private val lottoUseCase: LottoUseCase,
    private val latestDrawRound: Int,
    private val pageSize: Int
) : PagingSource<Int, LottoDrawResultModel>() {
    override fun getRefreshKey(state: PagingState<Int, LottoDrawResultModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LottoDrawResultModel> {
        return try {
            val page = params.key ?: 0
            val response = lottoUseCase.getLottoDrawResultList(
                latestRound = latestDrawRound - (page * pageSize),
                count = pageSize
            ).getOrThrow().map { it.toModel() }
            LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.size < pageSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

data class LottoResultHistoryState(
    val historyItemFlow : Flow<PagingData<LottoDrawResultModel>> = MutableStateFlow<PagingData<LottoDrawResultModel>>(PagingData.empty())
)

sealed class LottoResultHistorySideEffect {

}