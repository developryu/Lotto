package com.rhi.personal.lotto.presentation.ui.qrscan

import com.rhi.personal.lotto.presentation.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.rhi.personal.lotto.presentation.ui.permission.PermissionDialog
import com.rhi.personal.lotto.presentation.ui.permission.Permissions
import org.orbitmvi.orbit.compose.collectAsState

//    viewModel.scanQrCode("https://m.dhlottery.co.kr/?method=winQr&v=0949q142135364044q052330344344q030913314245q132432333738q0406151823261904598894%EF%BB%BF")

@Composable
fun QrScanScreen(
    viewModel: QrScanViewModel = hiltViewModel(),
    finish: () -> Unit
) {
    val state = viewModel.collectAsState().value
    var isShowPermissionDialog by remember { mutableStateOf(true) }
    if (isShowPermissionDialog) {
        PermissionDialog(
            permissions = listOf(Permissions.CAMERA),
            functionName = stringResource(R.string.permission_function_qr_scan),
            onGranted = { isShowPermissionDialog = false },
            onDenied = finish
        )
    } else {
        QrScanScreen()
    }
}

@Composable
private fun QrScanScreen() {

}

@Preview(showBackground = true)
@Composable
private fun QrScanScreenPreview() {
    QrScanScreen() {}
}