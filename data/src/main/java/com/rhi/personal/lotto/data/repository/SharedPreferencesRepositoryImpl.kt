package com.rhi.personal.lotto.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.rhi.personal.lotto.domain.repository.SharedPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class SharedPreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : SharedPreferencesRepository{
    companion object {
        private const val PREFERENCE_NAME = "my_pref"
        private const val IS_SHOULD_SHOW_RATIONALE = "is_should_show_rationale"
    }

    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private val prefs by lazy { getPreference(context) }
    private val editor by lazy { prefs.edit() }
    private val gson by lazy { Gson() }

    private fun putString(key: String, value: String?) {
        editor.putString(key, value)
        editor.apply()
    }

    private fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun putInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    private fun getString(key: String, defValue: String? = null): String? {
        return prefs.getString(key, defValue)
    }

    private fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return prefs.getBoolean(key, defValue)
    }

    private fun getInt(key: String, defValue: Int = 0): Int {
        return prefs.getInt(key, defValue)
    }

    private fun clear(key: String) {
        editor.remove(key)
        editor.apply()
    }

    override fun getPermissionIsShouldShowRationale(permission: String): Result<Boolean> = runCatching {
        getBoolean("$IS_SHOULD_SHOW_RATIONALE-$permission", false)
    }

    override suspend fun setPermissionIsShouldShowRationale(
        permission: String,
        isShouldShowRationale: Boolean
    ): Result<Boolean> = runCatching {
        putBoolean("$IS_SHOULD_SHOW_RATIONALE-$permission", isShouldShowRationale)
        isShouldShowRationale
    }

}