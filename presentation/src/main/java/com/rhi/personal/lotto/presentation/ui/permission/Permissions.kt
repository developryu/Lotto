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
    )

}