package com.rhi.personal.lotto.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LottoNumber(
    numbers: List<Int>,
    bonusNumber: Int,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = Color(0xFFEEEEEE)
    var ballSize by remember { mutableStateOf(30.dp) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.weight(0.75f)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            NumberBallList(
                numbers = numbers,
                onChangeBallSize = {
                    ballSize = it
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.size(5.dp))
        Box(
            modifier = Modifier.weight(0.05f),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                tint = Color(0xFF999999),
                modifier = Modifier.size(ballSize * 0.5f),
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.size(5.dp))
        Box(
            modifier = Modifier.weight(0.15f)

                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            NumberBall(
                number = bonusNumber,
                modifier = Modifier.size(ballSize)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LottoNumberPreview() {
    LottoNumber(
        numbers = listOf(7, 11, 21, 31, 41, 4),
        bonusNumber = 7,
        modifier = Modifier.fillMaxWidth().height(100.dp).padding(10.dp)
    )
}