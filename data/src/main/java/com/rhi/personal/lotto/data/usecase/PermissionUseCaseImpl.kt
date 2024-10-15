package com.rhi.personal.lotto.data.usecase

import com.rhi.personal.lotto.domain.repository.SharedPreferencesRepository
import com.rhi.personal.lotto.domain.usecase.PermissionUseCase
import javax.inject.Inject

class PermissionUseCaseImpl @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : PermissionUseCase {

    override fun getPermissionIsShouldShowRationale(permission: String): Result<Boolean> = runCatching {
        sharedPreferencesRepository.getPermissionIsShouldShowRationale(permission).getOrThrow()
    }

    override fun getPermissionIsShouldShowRationale(permissions: List<String>): Result<Boolean> = runCatching {
        var isShouldShowRationale = false
        permissions.forEach { permission ->
            isShouldShowRationale = isShouldShowRationale || getPermissionIsShouldShowRationale(permission).getOrThrow()
        }
        isShouldShowRationale
    }

    override suspend fun setPermissionIsShouldShowRationale(
        permission: String,
        isShouldShowRationale: Boolean
    ): Result<Unit> = runCatching {
        sharedPreferencesRepository.setPermissionIsShouldShowRationale(permission, isShouldShowRationale)
    }

    override suspend fun setPermissionIsShouldShowRationale(
        permissions: List<String>,
        isShouldShowRationale: Boolean
    ): Result<Unit> = runCatching {
        permissions.forEach { permission ->
            setPermissionIsShouldShowRationale(permission, isShouldShowRationale)
        }
    }

    override fun getCameraPermissionIsShouldShowRationale(): Result<Boolean> = runCatching {
        sharedPreferencesRepository.getPermissionIsShouldShowRationale(android.Manifest.permission.CAMERA).getOrThrow()
    }

    override suspend fun setCameraPermissionIsShouldShowRationale(isShouldShowRationale: Boolean): Result<Unit> = runCatching {
        sharedPreferencesRepository.setPermissionIsShouldShowRationale(android.Manifest.permission.CAMERA, isShouldShowRationale)
    }
}