package com.rhi.personal.lotto.presentation.widget.shownumber

import com.rhi.personal.lotto.presentation.R
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.action.Action
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.gson.Gson
import com.rhi.personal.lotto.presentation.model.LottoDrawResultModel
import com.rhi.personal.lotto.presentation.widget.WidgetPreferenceKey
import java.text.DateFormat
import java.util.Date
import java.util.Locale

class ShowNumberWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val preference = currentState<Preferences>()
            val dataStr = preference[WidgetPreferenceKey.keyShowNumber]
            val updateLong = preference[WidgetPreferenceKey.keyShowNumberUpdate]
            val refreshAction: Action = actionRunCallback<RefreshAction>()
            if (dataStr != null && updateLong != null) {
                val data = Gson().fromJson<LottoDrawResultModel>(dataStr, LottoDrawResultModel::class.java)
                ShowNumberWidget(id, data, Date(updateLong), refreshAction)
            } else {
                ShowNumberWidget(id, null, null, refreshAction)
            }
        }
    }

}

@Composable
fun ShowNumberWidget(
    id: GlanceId,
    data: LottoDrawResultModel?,
    update: Date?,
    refresh: Action? = null
) {
    val locale = Locale.getDefault()
    val dateFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.MEDIUM, locale)

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        RefreshAction().onAction(context, id, actionParametersOf())
    }

    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(GlanceTheme.colors.widgetBackground)
            .let {
                if (refresh != null) it.clickable(refresh) else it
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (data != null && update != null) {
            Text(
                text = String.format(
                    context.getString(R.string.widget_show_number_draw_round),
                    data.drawRound
                ),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorProvider(Color.Black, Color.White),
                )
            )
            Text(
                text = String.format(
                    context.getString(R.string.widget_show_number_draw_date),
                    data.getDrawDateShortFormat()
                ),
                style = TextStyle(
                    fontSize = 10.sp,
                    color = ColorProvider(Color.Gray, Color.Gray),
                    fontWeight = FontWeight.Normal
                )
            )

            Row(
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                val lastIndex = data.numbers.lastIndex
                data.numbers.forEachIndexed { index, it ->
                    WidgetLottoNumber(it, lastIndex != index)
                }
            }

            Text(
                modifier = GlanceModifier.fillMaxWidth()
                    .padding(end = 10.dp),
                text = "${dateFormat.format(update)}",
                style = TextStyle(
                    fontSize = 10.sp,
                    color = ColorProvider(Color.Gray, Color.Gray),
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.End
                ),
                maxLines = 1
            )
        } else {
            Text(
                text = stringResource(R.string.widget_show_number_loading)
            )
        }
    }
}

@Composable
fun WidgetLottoNumber(
    number: Int,
    isPadding: Boolean
) {
    val backgroundColor = when (number) {
        in 1..10 -> Color(0xFFFAC62C)   //0xFFFFA726
        in 11..20 -> Color(0xFF6CC7F0)
        in 21..30 -> Color(0xFFFE7576)  // 0xFFEF5350
        in 31..40 -> Color(0xFFAAAAAA)  // 회색 0xFF42A5F5
        else -> Color(0xFFB0D94B)
    }

    Box(
        modifier = GlanceModifier
            .let {
                if (isPadding) it.padding(end = 5.dp) else it
            },
    ) {
        Box(
            modifier = GlanceModifier
                .size(35.dp)
                .background(backgroundColor)
                .cornerRadius(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                style = TextStyle(
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = ColorProvider(Color.White, Color.White)
                )
            )
        }
    }
}

class RefreshAction : ActionCallback {
    override suspend fun onAction(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val workRequest = OneTimeWorkRequestBuilder<ShowNumberWidgetWorker>().build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }
}