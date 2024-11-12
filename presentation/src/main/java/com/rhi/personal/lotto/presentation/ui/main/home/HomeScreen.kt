package com.rhi.personal.lotto.presentation.ui.main.home

import com.rhi.personal.lotto.presentation.R
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.rhi.personal.lotto.presentation.ui.generaor.NumberGeneratorActivity
import com.rhi.personal.lotto.presentation.ui.history.LottoResultHistoryActivity
import com.rhi.personal.lotto.presentation.ui.main.dialog.LottoDrawResultDialog
import com.rhi.personal.lotto.presentation.ui.map.MapActivity
import com.rhi.personal.lotto.presentation.ui.qrscan.QrScanActivity
import org.orbitmvi.orbit.compose.collectAsState
import java.util.Date

@Composable
fun HomeScreen(
    preLoad: Pair<LottoDrawResultModel?, List<LottoDrawResultModel>?>,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.collectAsState().value
    viewModel.setHomeHeaderData(preLoad.first)
    viewModel.setHomeBodyData(preLoad.second)

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

    var isShowDialog by remember { mutableStateOf(false) }
    var selectedLottoDrawResult by remember { mutableStateOf<LottoDrawResultModel?>(null) }

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
                            selectedLottoDrawResult = model
                            isShowDialog = true
                        },
                        onClickQrScan = {
                            val intent = Intent(context, QrScanActivity::class.java)
                            context.startActivity(intent)
                        }
                    )
                }
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val intent = Intent(context, MapActivity::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = stringResource(R.string.map_button_title),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val intent = Intent(context, NumberGeneratorActivity::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 5.dp),
                        text = stringResource(R.string.number_generator_title),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (beforeLottoDrawResultList.isNullOrEmpty().not()) {
                homeTitle(
                    title = context.getString(R.string.home_title_before_lotto_draw_result),
                    menuTitle = context.getString(R.string.lotto_draw_result_history_button),
                    onClickMenu = {
                        val intent = Intent(context, LottoResultHistoryActivity::class.java)
                        context.startActivity(intent)
                    }
                )
                items(
                    count = beforeLottoDrawResultList.size
                ) {
                    beforeLottoDrawResultList.getOrNull(it)?.let { model ->
                        LottoDrawResultItem(
                            item = model,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            onClick = {
                                selectedLottoDrawResult = model
                                isShowDialog = true
                            }
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

        LottoDrawResultDialog(
            isVisible = isShowDialog,
            result = selectedLottoDrawResult,
            onDismissRequest = {
                selectedLottoDrawResult = null
                isShowDialog = false
            },
            onShredResult = {
                val text = String.format(
                    context.getString(R.string.share_lotto_draw),
                    it.drawRound,
                    it.numbers.joinToString(", "),
                    it.bonusNumber,
                    it.getPrizeFormat(it.firstPrizeAmount),
                    it.firstWinnerCount
                )
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, text)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }
        )


    }
}

private fun LazyListScope.homeTitle(
    title: String,
    menuTitle: String? = null,
    onClickMenu: (() -> Unit)? = null
) {
    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (menuTitle != null && onClickMenu != null) {
                    Text(
                        text = menuTitle,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.clickable(
                            onClick = onClickMenu
                        )
                    )
                }
            }
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