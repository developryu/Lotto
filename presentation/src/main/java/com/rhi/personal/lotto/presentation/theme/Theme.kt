package com.rhi.personal.lotto.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.rhi.personal.lotto.presentation.theme.custom.CustomLocalColorScheme
import com.rhi.personal.lotto.presentation.theme.custom.CustomLocalTypography
import com.rhi.personal.lotto.presentation.theme.custom.CustomTypography
import com.rhi.personal.lotto.presentation.theme.custom.customDarkColorScheme
import com.rhi.personal.lotto.presentation.theme.custom.customDarkMainColor
import com.rhi.personal.lotto.presentation.theme.custom.customLightColorScheme

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val LightCustomThemeColors = customLightColorScheme()

private val DarkCustomThemeColors = customDarkColorScheme(
    mainColor = customDarkMainColor
)

@Composable
fun LottoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
                context
            )
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val customColorScheme = when {
        darkTheme -> DarkCustomThemeColors
        else -> LightCustomThemeColors
    }

    val customTypography = CustomTypography

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = {
            CompositionLocalProvider(
                CustomLocalColorScheme provides customColorScheme,
                CustomLocalTypography provides customTypography
            ) {
                Surface(content = content)
            }
        }
    )
}