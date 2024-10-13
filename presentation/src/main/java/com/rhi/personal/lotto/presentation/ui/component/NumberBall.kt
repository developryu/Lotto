package com.rhi.personal.lotto.presentation.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun NumberBall(
    number: Int,
    color: Color = getBallColor(number),
    modifier: Modifier = Modifier,
) {
    var brushRadius by remember { mutableFloatStateOf(300f) }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .background(
                color = Color.White,
                shape = CircleShape
            )
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.5f),
                        color.copy(alpha = 0.2f),
                        color.copy(alpha = 0.5f),
                        color
                    ),
                    center = Offset(0.3f, 0.3f),
                    radius = brushRadius
                ),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        brushRadius = maxWidth.value.toFloat() * 1.5f
        with(LocalDensity.current) {
            Text(
                text = number.toString(),
                fontSize = (maxWidth * 0.65f).toSp(),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        offset = Offset(3f, 3f),
                        blurRadius = 4f
                    )
                )
            )
        }
    }
}

private fun getBallColor(number: Int): Color {
    return when (number) {
        in 1..10 -> Color(0xFFFAC62C)   //0xFFFFA726
        in 11..20 -> Color(0xFF6CC7F0)
        in 21..30 -> Color(0xFFFE7576)  // 0xFFEF5350
        in 31..40 -> Color(0xFFAAAAAA)  // 회색 0xFF42A5F5
        else -> Color(0xFFB0D94B)
    }
}

@Preview(showBackground = false)
@Composable
private fun NumberBallPreview() {
    NumberBall(
        number = 45,
        modifier = Modifier.size(200.dp)
    )
}