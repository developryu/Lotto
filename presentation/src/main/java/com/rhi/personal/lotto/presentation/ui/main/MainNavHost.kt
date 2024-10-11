package com.rhi.personal.lotto.presentation.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rhi.personal.lotto.presentation.ui.main.home.HomeScreen
import com.rhi.personal.lotto.presentation.ui.main.setting.SettingScreen
import com.rhi.personal.lotto.presentation.ui.main.splash.SplashScreen

@Composable
fun MainNavHost(
    viewModel: MainViewModel = hiltViewModel()
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

                }
            }
        ) { paddingValue ->
            NavHost(
                modifier = Modifier.padding(paddingValue),
                navController = navController,
                startDestination = MainRouteName.SPLASH_SCREEN
            ) {
                composable(route = MainRouteName.SPLASH_SCREEN) {
                    SplashScreen()
                }
                composable(route = MainRouteName.HOME_SCREEN) {
                    HomeScreen()
                }
                composable(route = MainRouteName.SETTING_SCREEN) {
                    SettingScreen()
                }
            }
        }
    }
}