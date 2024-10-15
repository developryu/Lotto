package com.rhi.personal.lotto.presentation.ui.main.dialog

import com.rhi.personal.lotto.presentation.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import com.rhi.personal.lotto.presentation.ui.component.LottoDrawResultView
import java.util.Date

@Composable
fun LottoDrawResultDialog(
    isVisible: Boolean,
    result: LottoDrawResultModel?,
    onDismissRequest: () -> Unit,
    onShredResult: (LottoDrawResultModel) -> Unit
) {
    if (!isVisible || result == null) return
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
                    .background(
                        color = Color.White,
                    )
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.lotto_draw_result_dialog_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                LottoDrawResultView(
                    drawRound = result.drawRound,
                    dateString = result.getDrawDateFormat(),
                    numbers = result.numbers,
                    bonusNumber = result.bonusNumber
                )
                LottoResultDetailContent(
                    title = stringResource(R.string.lotto_draw_result_dialog_sub_title1),
                    value = "₩ ${result.getPrizeFormat(result.firstPrizeAmount)}"
                )
                LottoResultDetailContent(
                    title = stringResource(R.string.lotto_draw_result_dialog_sub_title2),
                    value = String.format(stringResource(R.string.first_winner_count_value), result.firstWinnerCount)
                )
                LottoResultDetailContent(
                    title = stringResource(R.string.lotto_draw_result_dialog_sub_title3),
                    value = "₩ ${result.getPrizeFormat(result.firstPrizeTotalAmount)}"
                )
                LottoResultDetailContent(
                    title = stringResource(R.string.lotto_draw_result_dialog_sub_title4),
                    value = "₩ ${result.getPrizeFormat(result.totalSellAmount)}"
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextButton(
                        onClick = { onShredResult(result) }
                    ) {
                        Text(
                            text = stringResource(R.string.share)
                        )
                    }
                    TextButton(
                        onClick = onDismissRequest
                    ) {
                        Text(
                            text = stringResource(R.string.close)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LottoResultDetailContent(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = value,
            color = Color(0xFFD3391A),
            fontSize = 14.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LottoDrawResultDialogPreview() {
    LottoDrawResultDialog(
        isVisible = true,
        result = LottoDrawResultModel(
            drawRound = 1,
            drawDate = Date(),
            totalSellAmount = 102302304204,
            firstPrizeAmount = 1000000000,
            firstPrizeTotalAmount = 1000000000000,
            firstWinnerCount = 12,
            numbers = listOf(1, 2, 3, 4, 5, 6),
            bonusNumber = 7
        ),
        onDismissRequest = {},
        onShredResult = {}
    )
}