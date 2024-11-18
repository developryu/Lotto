package com.rhi.personal.lotto.presentation.widget.home

import com.rhi.personal.lotto.presentation.R
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.Action
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
import com.rhi.personal.lotto.presentation.ui.main.MainActivity

class HomeWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            HomeWidget(
                onClick = actionStartActivity<MainActivity>()
            )
        }
    }
}

@Composable
private fun HomeWidget(
    onClick: Action
) {
    val context = LocalContext.current
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
            provider = ImageProvider(R.drawable.ic_home),
            contentDescription = "이미지 설명",
            modifier = GlanceModifier.size(40.dp),
            colorFilter = ColorFilter.tint(ColorProvider(Color.Black, Color.White))
        )
        Spacer(modifier = GlanceModifier.size(10.dp))
        Text(
            text = context.getString(R.string.widget_home_title),
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = ColorProvider(Color.Black, Color.White),
            ),
            maxLines = 1,
            modifier = GlanceModifier.fillMaxWidth()
        )
    }
}