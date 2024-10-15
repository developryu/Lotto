package com.rhi.personal.lotto.presentation.ui.qrscan

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun QrScanNavHost(
    onFinished: () -> Unit,
    viewModel: QrScanViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is QrScanSideEffect.ShowQrScanResultScreen -> {
                navController.navigate(route = QrScanRouter.QrScanResultScreen.router,)
            }
        }
    }

    Surface {
        Scaffold { paddingValues ->
            NavHost(
                modifier = Modifier.padding(paddingValues),
                navController = navController,
                startDestination = QrScanRouter.QrScanScreen.router
            ) {
                composable(route = QrScanRouter.QrScanScreen.router) {
                    QrScanScreen(viewModel) {
                        onFinished()
                    }
                }
                composable(route = QrScanRouter.QrScanResultScreen.router,) {
                    QrScanResultScreen(
                        viewModel = viewModel,
                        onReScan = {
                            navController.popBackStack()
                            viewModel.reScanQrCode()
                        },
                        onFinished = onFinished
                    )
                }
            }
        }
    }
}

private enum class QrScanRouter(val router: String) {
    QrScanScreen("qrScanScreen"),
    QrScanResultScreen("qrScanResultScreen")
}