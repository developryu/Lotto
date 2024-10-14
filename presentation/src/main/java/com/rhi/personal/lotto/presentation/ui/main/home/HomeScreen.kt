package com.rhi.personal.lotto.presentation.ui.main.home

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import com.rhi.personal.lotto.presentation.ui.qrscan.QrScanActivity
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
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            latestLottoDrawResult?.let { model ->
                HomeHeader(
                    result = model,
                    onClickResult = {

                    },
                    onClickQrScan = {
                        val intent = Intent(context, QrScanActivity::class.java)
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        LottoDrawResultModel(1, Date(), 1L, 1L, 1L, 1, listOf(1, 11, 21, 31, 41, 51), 1),
        listOf(
            LottoDrawResultModel(1, Date(), 1L, 1L, 1L, 1, listOf(1, 1), 1),
            LottoDrawResultModel(1, Date(), 1L, 1L, 1L, 1, listOf(1, 1), 1)
        )
    )
}