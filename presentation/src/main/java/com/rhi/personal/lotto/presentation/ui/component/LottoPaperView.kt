package com.rhi.personal.lotto.presentation.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LottoPaperView(
    numbers: List<Int>,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var targetWidth = maxWidth
        var targetHeight = maxWidth / 9f * 16f
        if (targetHeight > maxHeight) {
            targetHeight = maxHeight
            targetWidth = maxHeight / 16f * 9f
        }

        LazyVerticalGrid(
            modifier = modifier
                .width(targetWidth)
                .height(targetHeight)
                .aspectRatio(9f/16f)
                .background(Color.White)
                .padding(1.dp),
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.SpaceBetween, //.spacedBy(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            userScrollEnabled = false
        ) {
            items(45) { number ->
                Number(num = number + 1, isSelected = numbers.contains(number + 1))
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun Number(
    num: Int,
    isSelected: Boolean
) {
    val density = LocalDensity.current
    var paddingValue by remember { mutableStateOf(0.dp) }
    val color = Color(0xFFD0362E)

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(9f/16f)
            .padding(paddingValue),
        contentAlignment = Alignment.Center
    ) {
        paddingValue = maxWidth * 0.1f
        val fontSize = with(density) { maxWidth.toSp() * 0.65f }
        NumberBackgroundLine(
            color = color,
            maxWidth = maxWidth,
            maxHeight = maxHeight
        )
        if (isSelected) {
            NumberSelectLine(
                maxWidth = maxWidth,
                maxHeight = maxHeight
            )
        }
        Text(
            text = num.toString(),
            style = TextStyle(
                fontSize = fontSize,
                color = color,
                fontWeight = FontWeight.Bold,
                shadow = if (isSelected) Shadow(
                    color = Color(0xFFAAAAAA),  // 그림자 색상
                    offset = Offset(1f, 1f),  // 그림자 위치 (x, y)
                    blurRadius = 5f  // 그림자 블러 정도
                ) else null
            )
        )
    }
}

@Composable
private fun NumberBackgroundLine(
    color: Color,
    maxWidth: Dp,
    maxHeight: Dp
) {
    val strokeWidthDp = maxWidth * 0.01f
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(maxWidth.toPx(), 0f),
            strokeWidth = strokeWidthDp.toPx(),
            cap = StrokeCap.Round
        )

        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(0f, maxHeight.toPx() * 0.15f),
            strokeWidth = strokeWidthDp.toPx(),
            cap = StrokeCap.Round
        )

        drawLine(
            color = color,
            start = Offset(maxWidth.toPx(), 0f),
            end = Offset(maxWidth.toPx(), maxHeight.toPx() * 0.15f),
            strokeWidth = strokeWidthDp.toPx(),
            cap = StrokeCap.Round
        )

        drawLine(
            color = color,
            start = Offset(0f, maxHeight.toPx() * 0.85f),
            end = Offset(0f, maxHeight.toPx()),
            strokeWidth = strokeWidthDp.toPx(),
            cap = StrokeCap.Round
        )

        drawLine(
            color = color,
            start = Offset(maxWidth.toPx(), maxHeight.toPx() * 0.85f),
            end = Offset(maxWidth.toPx(), maxHeight.toPx()),
            strokeWidth = strokeWidthDp.toPx(),
            cap = StrokeCap.Round
        )

        drawLine(
            color = color,
            start = Offset(0f, maxHeight.toPx()),
            end = Offset(maxWidth.toPx(), maxHeight.toPx()),
            strokeWidth = strokeWidthDp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun NumberSelectLine(
    maxWidth: Dp,
    maxHeight: Dp
) {
    val strokeWidthDp = maxWidth * 0.8f
    Canvas(
        modifier = Modifier.fillMaxSize().alpha(0.5f)
    ) {
        drawLine(
            color = Color.Black,
            start = Offset(maxWidth.toPx() * 0.5f, strokeWidthDp.toPx() * 0.8f),
            end = Offset(maxWidth.toPx() * 0.5f, maxHeight.toPx() - strokeWidthDp.toPx() * 0.8f),
            strokeWidth = strokeWidthDp.toPx(),
            cap = StrokeCap.Round
        )
    }
}