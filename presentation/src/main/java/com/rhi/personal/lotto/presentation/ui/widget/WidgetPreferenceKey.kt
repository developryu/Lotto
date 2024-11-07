package com.rhi.personal.lotto.presentation.ui.widget

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object WidgetPreferenceKey {
    val preferenceName = "widget_preference"
    val keyShowNumber = stringPreferencesKey("key_show_number")
    val keyShowNumberUpdate = longPreferencesKey("key_show_number_update")
}