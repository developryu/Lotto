package com.rhi.personal.lotto.presentation.theme.custom

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val CustomTypography = CustomMyTypography()



class CustomMyTypography(
    val defaultText: TextStyle = TextStyle(
        fontFamily = FontFamily.Companion.Default,
        fontWeight = FontWeight.Companion.Normal,
        fontSize = 14.sp,
        lineHeight = 16.0.sp,
        letterSpacing =  0.5.sp,
    )
)