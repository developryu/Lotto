package com.rhi.personal.lotto.presentation.ui.permission

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import com.rhi.personal.lotto.presentation.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import org.orbitmvi.orbit.compose.collectAsState

private enum class PermissionDialogScreenState {
    FIRST_REQUEST,
    SHOULD_SHOW_RATIONALE,
    SHOW_SETTINGS,
    NONE,
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionDialog(
    permissions: List<Permissions>,
    functionName: String,
    onGranted: () -> Unit,
    onDenied: () -> Unit,
    viewModel: PermissionViewModel = hiltViewModel(
        creationCallback = { factory: PermissionViewModel.PermissionViewModelFactory ->
            factory.create(permissions)
        }
    )
) {
    val state = viewModel.collectAsState().value
    val activity = LocalContext.current as Activity
    val lifecycleOwner = LocalLifecycleOwner.current
    var screenState by remember { mutableStateOf(PermissionDialogScreenState.NONE) }

    val permissionState = rememberMultiplePermissionsState(
        permissions = permissions.map { it.permission }.filter {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                it != android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        && it != android.Manifest.permission.READ_EXTERNAL_STORAGE
            } else {
                true
            }
        },
        onPermissionsResult = { isGranted ->
            if (isGranted.all { it.value }) {
                onGranted()
            } else {
                if (permissions.map { shouldShowRequestPermissionRationale(activity, it.permission) }.any { it }) {
                    viewModel.setShouldShowRationale()
                }
                onDenied()
            }
        }
    )

    LaunchedEffect(permissionState.allPermissionsGranted, state.isShouldShowRationale) {
        if (permissionState.allPermissionsGranted) {
            permissionState.launchMultiplePermissionRequest()
        } else {
            screenState = if (permissionState.shouldShowRationale) {
                PermissionDialogScreenState.SHOULD_SHOW_RATIONALE
            } else if (state.isShouldShowRationale.not()) {
                PermissionDialogScreenState.FIRST_REQUEST
            } else {
                PermissionDialogScreenState.SHOW_SETTINGS
            }
        }
    }
    if (screenState != PermissionDialogScreenState.NONE) {
        PermissionDialog(
            contentText = String.format(
                stringResource(R.string.permission_dialog_content),
                functionName,
                permissions.map { stringResource(it.permissionNameRes) }.joinToString(", ")
            ),
            okButtonText = stringResource(
                if (screenState == PermissionDialogScreenState.SHOW_SETTINGS)
                    R.string.permission_dialog_button_setting
                else
                    R.string.permission_dialog_button_ok
            ),
            onDismissRequest = {
                screenState = PermissionDialogScreenState.NONE
                onDenied()
            },
            onConfirmation = {
                if (screenState == PermissionDialogScreenState.SHOW_SETTINGS) {
                    moveToSetting(activity)
                } else {
                    permissionState.launchMultiplePermissionRequest()
                }
            },
        )
    }
}

@Composable
private fun PermissionDialog(
    contentText: String,
    okButtonText: String,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Row {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(stringResource(R.string.permission_dialog_button_cancel))
                }
                TextButton(
                    onClick = onConfirmation
                ) {
                    Text(okButtonText)
                }
            }
        },
        icon = {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Info"
            )
        },
        title = { Text(stringResource(R.string.permission_dialog_title)) },
        text = { Text(contentText) }
    )
}

private fun moveToSetting(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}