package com.rhi.personal.lotto.presentation.ui.history

import android.content.Intent
import androidx.compose.foundation.background
import com.rhi.personal.lotto.presentation.R
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import com.rhi.personal.lotto.presentation.ui.component.LottoDrawResultItem
import com.rhi.personal.lotto.presentation.ui.main.dialog.LottoDrawResultDialog
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun LottoResultHistoryScreen(
    viewModel: LottoResultHistoryViewModel = hiltViewModel(),
    finish: (() -> Unit)? = null
) {
    LaunchedEffect(Unit) {
        viewModel.getLottoResultHistoryPage()
    }

    val state = viewModel.collectAsState().value
    val list = state.historyItemFlow.collectAsLazyPagingItems()

    LottoResultHistoryScreen(list, finish)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LottoResultHistoryScreen(
    list: LazyPagingItems<LottoDrawResultModel>,
    finish: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var isShowDialog by remember { mutableStateOf(false) }
    var selectedLottoDrawResult by remember { mutableStateOf<LottoDrawResultModel?>(null) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val showTopButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.lotto_draw_result_history_title)) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                finish?.invoke()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                if (showTopButton) {
                    IconButton(
                        modifier = Modifier
                            .alpha(0.8f)
                            .background(
                                color = Color.Gray,
                                shape = CircleShape
                            ),
                        onClick = {
                            coroutineScope.launch {
                                listState.animateScrollToItem(0)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Move to top",
                            tint = Color.White
                        )
                    }
                }
            }
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.padding(it)
            ) {
                items(
                    count = list.itemCount,
                    key = { index -> list[index]?.drawRound ?: index }
                ) { index ->
                    list[index]?.let { model ->
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
                val text = "[${it.drawRound}회차 로또 당첨번호]\n" +
                        "당첨번호: ${it.numbers.joinToString(", ")} + ${it.bonusNumber}\n" +
                        "1등 당첨금: ${it.getPrizeFormat(it.firstPrizeAmount)}원\n" +
                        "1등 당첨자 수: ${it.firstWinnerCount}명"
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