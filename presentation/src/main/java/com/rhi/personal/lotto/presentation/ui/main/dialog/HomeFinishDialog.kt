package com.rhi.personal.lotto.presentation.ui.main.dialog

import com.rhi.personal.lotto.presentation.R
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource

@Composable
fun HomeFinishDialog(
    activity: ComponentActivity
) {
    var showDialog by remember { mutableStateOf(false) }
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showDialog = true
            }
        }
    }

    DisposableEffect(key1 = backCallback) {
        activity.onBackPressedDispatcher.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(R.string.home_finish_dialog_title)) },
            text = { Text(text = stringResource(R.string.home_finish_dialog_content)) },
            confirmButton = {
                TextButton(onClick = { activity.finish() }) {
                    Text(text = stringResource(R.string.home_finish_dialog_button_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = stringResource(R.string.home_finish_dialog_button_cancel))
                }
            }
        )
    }
}