package com.rhi.personal.lotto.domain.usecase

interface PermissionUseCase {

    fun getPermissionIsShouldShowRationale(permission: String): Result<Boolean>

    fun getPermissionIsShouldShowRationale(permissions: List<String>): Result<Boolean>

    suspend fun setPermissionIsShouldShowRationale(permission: String, isShouldShowRationale: Boolean): Result<Unit>

    suspend fun setPermissionIsShouldShowRationale(permissions: List<String>, isShouldShowRationale: Boolean): Result<Unit>

    fun getCameraPermissionIsShouldShowRationale(): Result<Boolean>

    suspend fun setCameraPermissionIsShouldShowRationale(isShouldShowRationale: Boolean): Result<Unit>


}