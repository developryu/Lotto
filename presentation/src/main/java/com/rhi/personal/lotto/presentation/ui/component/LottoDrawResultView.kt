package com.rhi.personal.lotto.presentation.ui.component

import com.rhi.personal.lotto.presentation.R
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Date

@SuppressLint("DefaultLocale")
@Composable
fun LottoDrawResultView(
    drawRound: Int,
    dateString: String,
    numbers: List<Int>,
    bonusNumber: Int,
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
                text = String.format(stringResource(R.string.draw_round), drawRound) + " ",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFD3391A)
            )
            Text(
                text = stringResource(R.string.winning_results),
                fontSize = 25.sp,
            )
        }
        Text(
            text = String.format(stringResource(R.string.draw_date), dateString),
            fontSize = 14.sp,
            color = Color.Gray
        )
        LottoNumber(
            modifier = Modifier.padding(top = 20.dp),
            numbers = numbers,
            bonusNumber = bonusNumber
        )
        Row(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(
                modifier = Modifier.weight(0.75f),
                textAlign = TextAlign.Center,
                color = Color(0xFF555555),
                text = stringResource(R.string.winning_number),
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                modifier = Modifier.weight(0.15f),
                text = stringResource(R.string.winning_bonus_number),
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
        drawRound = 1111,
        dateString = Date().toString(),
        numbers = listOf(1, 11, 21, 31, 41, 2),
        bonusNumber = 1
    )
}