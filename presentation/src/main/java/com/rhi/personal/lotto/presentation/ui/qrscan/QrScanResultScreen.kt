package com.rhi.personal.lotto.presentation.ui.qrscan

import com.rhi.personal.lotto.presentation.R
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import com.rhi.personal.lotto.presentation.model.LottoQrScanResultModel
import com.rhi.personal.lotto.presentation.model.MyNumberModel
import com.rhi.personal.lotto.presentation.ui.component.LottoDrawResultView
import com.rhi.personal.lotto.presentation.ui.component.NumberBallList
import com.rhi.personal.lotto.presentation.ui.main.dialog.LottoDrawResultDialog
import org.orbitmvi.orbit.compose.collectAsState
import java.util.Date

@Composable
fun QrScanResultScreen(
    viewModel: QrScanViewModel = hiltViewModel(),
    onReScan: () -> Unit,
    onFinished: () -> Unit,
) {
    val state = viewModel.collectAsState().value
    state.qrScanResult?.let {
        QrScanResultScreen(it, onReScan, onFinished)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScanResultScreen(
    result: LottoQrScanResultModel,
    onReScan: () -> Unit,
    onFinished: () -> Unit,
) {
    val context = LocalContext.current
    var isShowDialog by remember { mutableStateOf(false) }

    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.qr_scan_result_title)) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onReScan()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.padding(it)
            ) {
                LottoDrawResultView(
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { isShowDialog = true },
                    drawRound = result.drawResult.drawRound,
                    dateString = result.drawResult.getDrawDateFormat(),
                    numbers = result.drawResult.numbers,
                    bonusNumber = result.drawResult.bonusNumber
                )

                WinningResultsText(
                    result = result
                )

                Text(
                    text = stringResource(R.string.qr_scan_result_my_number_title),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(20.dp)
                )
                result.myNumber.forEach { numbers ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = numbers.order,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = numbers.resultTitle,
                            fontSize = 14.sp
                        )
                        NumberBallList(
                            numbers = numbers.numbers,
                            drawResult = result.drawResult.numbers,
                            onChangeBallSize = {},
                            modifier = Modifier.wrapContentWidth().padding(10.dp).height(35.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { onReScan() }
                    ) {
                        Text(
                            text = stringResource(R.string.qr_scan_result_rescan),
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result.url))
                            context.startActivity(intent)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.qr_scan_result_move_browser),
                            fontSize = 12.sp

                        )
                    }

                }
                Button(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    onClick = { onFinished() }
                ) {
                    Text(
                        text = stringResource(R.string.qr_scan_result_close),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

    LottoDrawResultDialog(
        isVisible = isShowDialog,
        result = result.drawResult,
        onDismissRequest = {
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

@Composable
private fun WinningResultsText(result: LottoQrScanResultModel) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = result.resultTitle1,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
        )

        Text(
            textAlign = TextAlign.Center,
            text = result.resultTitle2,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}


@Preview(showBackground = false)
@Composable
fun QrScanResultScreenPreview() {
    QrScanResultScreen(
        result = LottoQrScanResultModel(
            drawResult = LottoDrawResultModel(
                drawRound = 1123,
                drawDate = Date(),
                totalSellAmount = 1111,
                firstPrizeAmount = 1111,
                firstPrizeTotalAmount = 1111,
                firstWinnerCount = 11,
                numbers = listOf(1, 12, 23, 34, 45, 46),
                bonusNumber = 3,
            ),
            url = "",
            myNumber = listOf(
                MyNumberModel(listOf(1, 2, 3, 4, 5, 6), "낙첨", "A"),
                MyNumberModel(listOf(11, 12, 13, 14, 15, 16), "낙첨", "A"),
                MyNumberModel(listOf(21, 22, 23, 24, 25, 26), "낙첨", "A"),
                MyNumberModel(listOf(31, 32, 33, 34, 35, 36), "낙첨", "A"),
                MyNumberModel(listOf(1, 12, 23, 34, 45, 46), "낙첨", "A"),
            ),
            resultTitle1 = "축하합니다!",
            resultTitle2 = "(1, 2, 3)등 당첨되었습니다."
        ),
        onReScan = {},
        onFinished = {}
    )
}