package com.rhi.personal.lotto.presentation.ui.main

import com.rhi.personal.lotto.presentation.R
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import com.rhi.personal.lotto.presentation.model.SplashPreLoadModel
import com.rhi.personal.lotto.presentation.ui.main.home.HomeScreen
import com.rhi.personal.lotto.presentation.ui.main.setting.SettingScreen
import kotlinx.coroutines.launch

@Composable
fun MainNavHost(
    preLoadModel: SplashPreLoadModel?
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { MainNavigationDrawer() }
    ) {
        MainNavHost(
            preLoadModel?.latestLottoDrawResult,
            preLoadModel?.beforeLottoDrawResultList,
            onClickOpenNavigationDrawer = {
                scope.launch {
                    drawerState.open()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainNavHost(
    latestLottoDrawResult: LottoDrawResultModel?,
    beforeLottoDrawResultList: List<LottoDrawResultModel>?,
    onClickOpenNavigationDrawer: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Surface {
        Scaffold(
            topBar = {
                if (MainRouter.MainNav.isMainNav(currentRoute)) {
                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(R.string.app_name)
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    onClickOpenNavigationDrawer()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "menu"
                                )
                            }
                        }
                    )
                }
            },
            bottomBar = {
                if (MainRouter.MainNav.isMainNav(currentRoute)) {
                    MainBottomBar(
                        currentRoute = currentRoute ?: MainRouter.MainNav.Home.route,
                        onClick = { newRoute ->
                            navController.navigate(newRoute) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) { saveState = true }
                                    this.launchSingleTop = true
                                    this.restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        ) { paddingValue ->
            NavHost(
                modifier = Modifier.padding(paddingValue),
                navController = navController,
                startDestination = MainRouteName.HOME_SCREEN
            ) {
                composable(route = MainRouteName.HOME_SCREEN) {
                    HomeScreen(latestLottoDrawResult to beforeLottoDrawResultList)
                }
                composable(route = MainRouteName.SETTING_SCREEN) {
                    SettingScreen()
                }
            }
        }
    }
}