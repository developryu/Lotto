package com.rhi.personal.lotto.presentation.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rhi.personal.lotto.presentation.model.SplashPreLoadModel
import com.rhi.personal.lotto.presentation.ui.main.home.HomeScreen
import com.rhi.personal.lotto.presentation.ui.main.setting.SettingScreen

@Composable
fun MainNavHost(
    preLoadModel: SplashPreLoadModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Surface {
        Scaffold(
            topBar = {
                if (MainRouter.MainNav.isMainNav(currentRoute)) {

                }
            },
            bottomBar = {
                if (MainRouter.MainNav.isMainNav(currentRoute)) {
                    MainBottomBar(currentRoute ?: MainRouter.MainNav.Home.route) { newRoute ->
                        navController.navigate(newRoute) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) { saveState = true }
                                this.launchSingleTop = true
                                this.restoreState = true
                            }
                        }
                    }
                }
            }
        ) { paddingValue ->
            NavHost(
                modifier = Modifier.padding(paddingValue),
                navController = navController,
                startDestination = MainRouteName.HOME_SCREEN
            ) {
                composable(route = MainRouteName.HOME_SCREEN) {
                    HomeScreen(preLoadModel.latestLottoDrawResult to preLoadModel.beforeLottoDrawResultList)
                }
                composable(route = MainRouteName.SETTING_SCREEN) {
                    SettingScreen()
                }
            }
        }
    }
}