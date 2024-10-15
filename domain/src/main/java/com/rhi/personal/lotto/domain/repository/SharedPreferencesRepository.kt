package com.rhi.personal.lotto.domain.repository

interface SharedPreferencesRepository {

    fun getPermissionIsShouldShowRationale(permission: String): Result<Boolean>

    suspend fun setPermissionIsShouldShowRationale(permission: String, isShouldShowRationale: Boolean): Result<Boolean>
}