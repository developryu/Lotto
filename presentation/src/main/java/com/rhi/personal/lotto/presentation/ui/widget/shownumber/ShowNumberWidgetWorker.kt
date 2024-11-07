package com.rhi.personal.lotto.presentation.ui.widget.shownumber

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.rhi.personal.lotto.domain.usecase.LottoUseCase
import com.rhi.personal.lotto.presentation.model.toModel
import com.rhi.personal.lotto.presentation.ui.widget.WidgetPreferenceKey
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ShowNumberWidgetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val lottoUseCase: LottoUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val data = lottoUseCase.getLatestLottoDrawResult().getOrThrow().toModel()
            val dataString = Gson().toJson(data)
            val update = System.currentTimeMillis()
            val manager = GlanceAppWidgetManager(context)
            val glanceIds = manager.getGlanceIds(ShowNumberWidget::class.java)
            glanceIds.forEach { id ->
                updateAppWidgetState(context, id) {
                    it[WidgetPreferenceKey.keyShowNumber] = dataString
                    it[WidgetPreferenceKey.keyShowNumberUpdate] = update
                }
                ShowNumberWidget().update(context, id)
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

}