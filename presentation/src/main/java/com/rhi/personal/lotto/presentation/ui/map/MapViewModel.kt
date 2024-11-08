package com.rhi.personal.lotto.presentation.ui.map

import android.content.Context
import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLngBounds
import com.rhi.personal.lotto.domain.usecase.SellLottoMarkerUseCase
import com.rhi.personal.lotto.presentation.model.SellLottoMarkerModel
import com.rhi.personal.lotto.presentation.model.toModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStream
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class MapViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sellLottoMarkerUseCase: SellLottoMarkerUseCase
) : ViewModel(), ContainerHost<MapState, MapSideEffect> {

    override val container: Container<MapState, MapSideEffect> = container(
        initialState = MapState()
    )

    private val markerFileNameStart = "sell_lotto_geo_"
    private val markerFileNameEnd = ".csv"

    fun initMarkerData() = intent {
        var assetManager: AssetManager? = null
        var inputStream: InputStream? = null
        var reader: BufferedReader? = null

        try {
            assetManager = context.assets
            val (fileName, updateDate) = getMarkerFileName(assetManager) ?: return@intent
            inputStream = assetManager.open(fileName)
            reader = inputStream.bufferedReader()
            val lines = reader.readLines()
            val dataLines = lines.drop(1)
            val markers = mutableListOf<SellLottoMarkerModel>()

            for (line in dataLines) {
                try {
                    val data = line.replace("\"", "").split(",")
                    markers.add(SellLottoMarkerModel(
                        date = updateDate,
                        no = data[0].trim().toInt(),
                        name = data[1].trim(),
                        address1 = data[2].trim(),
                        address2 = data[3].trim(),
                        longitude = if (data.size == 6) data[4].trim().toDouble() else null,
                        latitude = if (data.size == 6) data[5].trim().toDouble() else null
                    ))
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
            val size = sellLottoMarkerUseCase.getSellLottoMarkerSize(updateDate).getOrThrow()
            if (markers.size != size) {
                sellLottoMarkerUseCase.insertSellLottoMarker(markers.map { it.toDomain() }).getOrThrow()
            }
            reduce {
                state.copy(isLoading = false)
            }
        } catch (e: Exception) {
            Timber.e(e)
        } finally {
            inputStream?.close()
            reader?.close()
        }
    }

    private fun getMarkerFileName(manager: AssetManager) : Pair<String, Int>? {
        val files = manager.list("")?.toList() ?: emptyList()
        var date: Int? = null
        files.filter { it.startsWith(markerFileNameStart) && it.endsWith(markerFileNameEnd)  }.forEach {
            try {
                val fileDate = it.substringAfter(markerFileNameStart).substringBefore(markerFileNameEnd).toInt()
                if (fileDate > (date ?: 0)) {
                    date = fileDate
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
        return if (date == null) null else Pair("$markerFileNameStart$date$markerFileNameEnd", date)
    }

    fun getMarkers(bounds: LatLngBounds) = intent {
        val markers = sellLottoMarkerUseCase.getSellLottoMarkerBounds(
            bounds.southWest.latitude,
            bounds.southWest.longitude,
            bounds.northEast.latitude,
            bounds.northEast.longitude
        ).getOrThrow()

        val sorted = if (markers.size > 100) {
            val centerLat = (bounds.southWest.latitude + bounds.northEast.latitude) / 2
            val centerLng = (bounds.southWest.longitude + bounds.northEast.longitude) / 2
            markers.sortedBy {
                abs((it.latitude ?: 0.0) - centerLat) + abs((it.longitude ?: 0.0) - centerLng)
            }.take(100)
        } else {
            markers
        }
        reduce {
            state.copy(
                markers= sorted.map { it.toModel() }
            )
        }
    }
}

data class MapState(
    val isLoading: Boolean = true,
    val markers: List<SellLottoMarkerModel> = emptyList()
)

sealed class MapSideEffect {

}