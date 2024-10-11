package com.rhi.personal.lotto.presentation.theme.custom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

object CustomMaterialTheme {
    val colorScheme: CustomColorScheme
        @Composable
        @ReadOnlyComposable
        get() = CustomLocalColorScheme.current

    val typography: CustomMyTypography
        @Composable
        @ReadOnlyComposable
        get() = CustomLocalTypography.current
}

internal val CustomLocalColorScheme = staticCompositionLocalOf { customLightColorScheme() }
internal val CustomLocalTypography = staticCompositionLocalOf { CustomMyTypography() }