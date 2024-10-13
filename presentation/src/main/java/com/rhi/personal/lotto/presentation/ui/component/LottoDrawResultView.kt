package com.rhi.personal.lotto.presentation.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import java.util.Date

@SuppressLint("DefaultLocale")
@Composable
fun LottoDrawResultView(
    result: LottoDrawResultModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = String.format("%02d회차", result.drawRound) + " ",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD3391A)
            )
            Text(
                text = "당첨결과",
                fontSize = 25.sp,
            )
        }
        Text(
            text = String.format("(%s 추첨)", result.getDrawDateFormat()),
            fontSize = 14.sp,
            color = Color.Gray
        )
        LottoNumber(
            modifier = Modifier.padding(top = 20.dp),
            numbers = result.numbers,
            bonusNumber = result.bonusNumber
        )
        Row(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(
                modifier = Modifier.weight(0.75f),
                textAlign = TextAlign.Center,
                color = Color(0xFF555555),
                text = "당첨번호",
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                modifier = Modifier.weight(0.15f),
                text = "보너스",
                color = Color(0xFF555555),
                textAlign = TextAlign.Center,
                fontSize = 10.sp
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun LottoDrawResultViewPreview() {
    LottoDrawResultView(
        result = LottoDrawResultModel(
            drawRound = 1111,
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