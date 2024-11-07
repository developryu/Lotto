package com.rhi.personal.lotto.presentation.ui.widget.home

import androidx.glance.appwidget.GlanceAppWidgetReceiver

class HomeWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: HomeWidget
        get() = HomeWidget()
}