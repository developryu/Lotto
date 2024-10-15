package com.rhi.personal.lotto.presentation.ui.qrscan

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun QrScanResultScreen(
    viewModel: QrScanViewModel = hiltViewModel(),
    onReScan: () -> Unit,
    onFinished: () -> Unit,
) {
    val state = viewModel.collectAsState().value
    Text(text = "QrScreen2 ${state.qrScanResult}")
}