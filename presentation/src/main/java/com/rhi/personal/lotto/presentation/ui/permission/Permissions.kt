package com.rhi.personal.lotto.presentation.ui.permission

import com.rhi.personal.lotto.presentation.R
import androidx.annotation.StringRes

enum class Permissions(
    val permission: String,
    @StringRes val permissionNameRes: Int
) {
    CAMERA(
        permission = android.Manifest.permission.CAMERA,
        permissionNameRes = R.string.permission_camera
    ),

    WRITE_EXTERNAL_STORAGE(
        permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        permissionNameRes = R.string.permission_write_external_storage
    ),

    READ_EXTERNAL_STORAGE(
        permission = android.Manifest.permission.READ_EXTERNAL_STORAGE,
        permissionNameRes = R.string.permission_read_external_storage
    )

}
