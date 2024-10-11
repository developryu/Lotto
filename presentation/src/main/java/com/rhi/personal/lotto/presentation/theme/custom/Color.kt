package com.rhi.personal.lotto.presentation.theme.custom

import androidx.compose.ui.graphics.Color

val customLightMainColor = Color(0xFFE0E0E0)

val customDarkMainColor = Color(0xFF121212)

fun customLightColorScheme(
    mainColor: Color = customLightMainColor
) : CustomColorScheme {
    return CustomColorScheme(
        mainColor = mainColor
    )
}

fun customDarkColorScheme(
    mainColor: Color = customDarkMainColor
) : CustomColorScheme {
    return CustomColorScheme(
        mainColor = mainColor
    )
}

class CustomColorScheme(
    val mainColor: Color
)

