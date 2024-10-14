package com.rhi.personal.lotto.presentation.ui.main.home

import androidx.compose.foundation.Image
import com.rhi.personal.lotto.presentation.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import com.rhi.personal.lotto.presentation.theme.AppMainColor
import com.rhi.personal.lotto.presentation.ui.component.LottoDrawResultView
import java.util.Date

@Composable
fun HomeHeader(
    result: LottoDrawResultModel,
    onClickResult: (model: LottoDrawResultModel) -> Unit = {},
    onClickQrScan: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(bottom = 10.dp)
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 10.dp
            )
        ) {
            LottoDrawResultView(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClickResult(result)
                },
                result = result
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
                    .height(50.dp)
                    .background(
                        color = AppMainColor,
                        shape = RoundedCornerShape(25.dp)
                    )
                    .clickable { onClickQrScan() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(R.drawable.ic_qrcode),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.qr_code_scan),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }
    }

}

@Preview(showBackground = false)
@Composable
private fun HomeHeaderPreview() {
    HomeHeader(
        result = LottoDrawResultModel(
            drawRound = 1,
            drawDate = Date(),
            totalSellAmount = 1,
            firstPrizeAmount = 1,
            firstPrizeTotalAmount = 1,
            firstPrizeWinnerCount = 1,
            numbers = listOf(1, 11, 21, 31, 41, 2),
            bonusNumber = 1,
        )
    )
}