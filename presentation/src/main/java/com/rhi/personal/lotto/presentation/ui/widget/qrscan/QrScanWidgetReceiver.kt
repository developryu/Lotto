package com.rhi.personal.lotto.presentation.ui.widget.qrscan

import androidx.glance.appwidget.GlanceAppWidgetReceiver

class QrScanWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: QrScanWidget
        get() = QrScanWidget()
}