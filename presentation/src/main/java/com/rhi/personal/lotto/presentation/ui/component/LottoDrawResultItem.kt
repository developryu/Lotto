package com.rhi.personal.lotto.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rhi.personal.lotto.presentation.R
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import java.util.Date

@Composable
fun LottoDrawResultItem(
    item: LottoDrawResultModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = String.format(stringResource(R.string.draw_round), item.drawRound),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = item.getDrawDateShortFormat(),
                fontSize = 10.sp
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        LottoNumber(
            numbers = item.numbers,
            bonusNumber = item.bonusNumber,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LottoDrawResultItemPreview() {
    LottoDrawResultItem(
        LottoDrawResultModel(1139, Date(), 1, 1, 1, 1, listOf(1, 11, 21, 31, 41, 6), 1)
    )
}