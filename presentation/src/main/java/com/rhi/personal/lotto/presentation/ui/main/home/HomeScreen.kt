package com.rhi.personal.lotto.presentation.ui.main.home

import com.rhi.personal.lotto.presentation.R
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import com.rhi.personal.lotto.presentation.ui.component.LottoDrawResultItem
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
    HomeScreen(
        latestLottoDrawResult = state.latestLottoDrawResult,
        beforeLottoDrawResultList = state.beforeLottoDrawResultList,
        onMoreLoadClick = viewModel::onMoreLoadClick
    )
}

@Composable
private fun HomeScreen(
    latestLottoDrawResult: LottoDrawResultModel?,
    beforeLottoDrawResultList: List<LottoDrawResultModel>?,
    onMoreLoadClick: (latestIndex: Int) -> Unit = {},
) {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 10.dp)
        ) {
            latestLottoDrawResult?.let { model ->
                item {
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

            if (beforeLottoDrawResultList.isNullOrEmpty().not()) {
                homeTitle(title = context.getString(R.string.home_title_before_lotto_draw_result))
                items(
                    count = beforeLottoDrawResultList.size
                ) {
                    beforeLottoDrawResultList.getOrNull(it)?.let { model ->
                        LottoDrawResultItem(
                            item = model,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }
                }
                if (beforeLottoDrawResultList.last().drawRound > 1) {
                    item {
                        Text(
                            text = stringResource(R.string.more_load),
                            fontSize = 14.sp,
                            color = Color(0xFF999999),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(4.dp)
                                .clickable(
                                    onClick = {
                                        onMoreLoadClick(beforeLottoDrawResultList.last().drawRound - 1)
                                    }
                                )
                        )
                    }
                }
            }
        }
    }
}

private fun LazyListScope.homeTitle(title: String) {
    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(4.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 2.dp)
            )
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