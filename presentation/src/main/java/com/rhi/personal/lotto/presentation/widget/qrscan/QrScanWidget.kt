package com.rhi.personal.lotto.presentation.widget.qrscan

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.Action
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.rhi.personal.lotto.presentation.R
import com.rhi.personal.lotto.presentation.ui.main.MainActivity
import com.rhi.personal.lotto.presentation.ui.qrscan.QrScanActivity

class QrScanWidget : GlanceAppWidget() {

    val destinationKey = ActionParameters.Key<String>(MainActivity.KEY_DESTINATION)

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            QrScanWidget(
                onClick = actionStartActivity<MainActivity>(
                    actionParametersOf(destinationKey to QrScanActivity::class.java.simpleName)
                )
            )
        }
    }

}

@Composable
fun QrScanWidget(
    onClick: Action
) {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(GlanceTheme.colors.widgetBackground)
            .clickable(
                onClick = onClick
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            provider = ImageProvider(R.drawable.ic_qrcode),
            contentDescription = "이미지 설명",
            modifier = GlanceModifier.size(40.dp)
        )
        Spacer(modifier = GlanceModifier.size(10.dp))
        Text(
            text = "QR 스캔",
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = ColorProvider(Color.Black, Color.White),
            ),
            maxLines = 1,
            modifier = GlanceModifier.fillMaxWidth()
        )
    }
}