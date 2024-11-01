package com.rhi.personal.lotto.presentation.ui.qrscan

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import com.rhi.personal.lotto.presentation.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rhi.personal.lotto.presentation.ui.permission.PermissionDialog
import com.rhi.personal.lotto.presentation.ui.permission.Permissions
import com.ryu.personal.android.qrscanutil.ui.QrScanViewScreen
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
            permissions = listOf(Permissions.CAMERA, Permissions.READ_EXTERNAL_STORAGE, Permissions.WRITE_EXTERNAL_STORAGE),
            functionName = stringResource(R.string.permission_function_qr_scan),
            onGranted = { isShowPermissionDialog = false },
            onDenied = finish
        )
    } else {
        QrScanScreen(
            onQrDetected = { viewModel.scanQrCode(it) },
            finish = finish
        )
    }

    LaunchedEffect(Unit) {
        viewModel.reScanQrCode()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun QrScanScreen(onQrDetected: (String) -> Unit, finish: () -> Unit) {
    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.qr_scan_screen_title),
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                finish()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) {


            BoxWithConstraints(
                modifier = Modifier.padding(it).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val offsetY = maxHeight * (-0.1f)
                val qrScannerModifier = if (maxWidth > maxHeight)
                    Modifier.fillMaxHeight(0.8f).aspectRatio(1f)
                else
                    Modifier.fillMaxWidth(0.8f).aspectRatio(1f)
                Column(
                    modifier = Modifier.offset(y = offsetY),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    QrScanViewScreen(
                        modifier = qrScannerModifier
                            .background(Color.Green),
                        onQrDetected = onQrDetected
                    )
                    Text(
                        text = stringResource(R.string.qr_scan_screen_description),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun QrScanScreenPreview() {
    QrScanScreen(
        onQrDetected = {},
        finish = {}
    )
}