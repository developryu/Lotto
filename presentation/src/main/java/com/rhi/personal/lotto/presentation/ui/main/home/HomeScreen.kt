package com.rhi.personal.lotto.presentation.ui.main.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import org.orbitmvi.orbit.compose.collectAsState
import java.util.Date

@Composable
fun HomeScreen(
    preLoad: Pair<LottoDrawResultModel?, List<LottoDrawResultModel>?>,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.collectAsState().value
    viewModel.setInitData(preLoad.first, preLoad.second)
    HomeScreen(state.latestLottoDrawResult, state.beforeLottoDrawResultList)
}

@Composable
private fun HomeScreen(
    latestLottoDrawResult: LottoDrawResultModel?,
    beforeLottoDrawResultList: List<LottoDrawResultModel>?,
) {
    Text(text = "HomeScreen ${latestLottoDrawResult?.drawRound}")
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        LottoDrawResultModel(1, Date(), 1L, 1L, 1L, 1, listOf(1, 1), 1),
        listOf(
            LottoDrawResultModel(1, Date(), 1L, 1L, 1L, 1, listOf(1, 1), 1),
            LottoDrawResultModel(1, Date(), 1L, 1L, 1L, 1, listOf(1, 1), 1)
        )
    )
}