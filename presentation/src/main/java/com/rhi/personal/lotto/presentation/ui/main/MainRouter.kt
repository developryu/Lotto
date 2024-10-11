package com.rhi.personal.lotto.presentation.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rhi.personal.lotto.presentation.ui.main.MainRouteName.HOME_SCREEN
import com.rhi.personal.lotto.presentation.ui.main.MainRouteName.SETTING_SCREEN
import com.rhi.personal.lotto.presentation.ui.main.MainRouteName.SPLASH_SCREEN

sealed class MainRouter(open val route: String) {

    sealed class MainNav(
        override val route: String,
        @StringRes val title: Int,
        @DrawableRes val iconDrawableRes: Int,
        val contentDescription: String
    ): MainRouter(route) {
        data object Home: MainNav(HOME_SCREEN, 0, 0, "home")
        data object Setting: MainNav(SETTING_SCREEN, 0, 0, "setting")

        companion object {
            fun isMainNav(route: String?): Boolean {
                return when (route) {
                    HOME_SCREEN,
                    SETTING_SCREEN -> true
                    else -> false
                }
            }
        }
    }

    sealed class SubNav(
        override val route: String
    ): MainRouter(route) {
        data object Splash: SubNav(SPLASH_SCREEN)
    }

}

object MainRouteName {
    const val SPLASH_SCREEN = "splash_screen"
    const val HOME_SCREEN = "home_screen"
    const val SETTING_SCREEN = "setting_screen"
}