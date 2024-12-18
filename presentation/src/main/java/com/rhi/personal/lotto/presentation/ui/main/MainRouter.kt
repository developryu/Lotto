package com.rhi.personal.lotto.presentation.ui.main

import com.rhi.personal.lotto.presentation.R
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.rhi.personal.lotto.presentation.ui.main.MainRouteName.HOME_SCREEN
import com.rhi.personal.lotto.presentation.ui.main.MainRouteName.SETTING_SCREEN

sealed class MainRouter(open val route: String) {

    sealed class MainNav(
        override val route: String,
        @StringRes val title: Int,
        @DrawableRes val iconDrawableRes: Int,
        val contentDescription: String
    ): MainRouter(route) {
        data object Home: MainNav(HOME_SCREEN, R.string.main_nav_home, R.drawable.ic_home, "home")
        data object Setting: MainNav(SETTING_SCREEN, R.string.main_nav_setting, R.drawable.ic_setting, "setting")

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

    }

}

object MainRouteName {
    const val HOME_SCREEN = "home_screen"
    const val SETTING_SCREEN = "setting_screen"
}