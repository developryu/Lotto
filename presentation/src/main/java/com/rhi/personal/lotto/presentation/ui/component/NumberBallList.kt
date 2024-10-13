package com.rhi.personal.lotto.presentation.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun NumberBallList(
    numbers: List<Int>,
    gap: Dp = 8.dp,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    var ballSize by remember { mutableStateOf(30.dp) }
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ballSize = with(density) {
            val baseWidth = (maxWidth - (gap * (numbers.size - 1))) / numbers.size
            if (baseWidth > maxHeight) maxHeight else baseWidth
        }
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(gap)
        ) {
            items(numbers.size) { index ->
                NumberBall(
                    number = numbers[index],
                    modifier = Modifier
                        .size(ballSize)
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun NumberBallListPreview() {
    NumberBallList(
        numbers = listOf(1, 11, 21, 31, 41, 4),
        modifier = Modifier
            .width(400.dp)
            .wrapContentHeight()
    )
}