package com.rhi.personal.lotto.presentation.ui.qrscan

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun QrScanScreen(
    viewModel: QrScanViewModel = hiltViewModel()
) {
    viewModel.scanQrCode("https://m.dhlottery.co.kr/?method=winQr&v=0949q142135364044q052330344344q030913314245q132432333738q0406151823261904598894%EF%BB%BF")
    Text(text = "QrScreen")
}

@Preview(showBackground = true)
@Composable
private fun QrScanScreenPreview() {
    QrScanScreen()
}