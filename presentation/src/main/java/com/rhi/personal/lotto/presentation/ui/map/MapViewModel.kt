package com.rhi.personal.lotto.presentation.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.rhi.personal.lotto.domain.usecase.SellLottoMarkerUseCase
import com.rhi.personal.lotto.presentation.R
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

    @SuppressLint("DefaultLocale")
    fun getMarkers(bounds: LatLngBounds, myLocation: LatLng?) = intent {
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
        }.map { it.toModel() }

        myLocation?.let { loc ->
            sorted.forEach {
                getDistanceToMeter(loc, LatLng(it.latitude!!, it.longitude!!)).let { distance ->
                    it.distance = distance
                    if (distance > 1000) {
                        it.distanceString = String.format("%.1fkm", distance / 1000)
                    } else {
                        it.distanceString = String.format("%.1fm", distance)
                    }
                }
            }
            sorted.sortedBy { it.distance }
        }

        reduce {
            state.copy(
                markers= sorted
            )
        }
    }

    fun openNaverMap(context: Context, myLocation: LatLng?, sellLottoMarker: SellLottoMarkerModel) {
        try {
            val naverMapPackageName = "com.nhn.android.nmap"
            val isInstallNaverMap = try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val pm = context.packageManager
                    pm.getPackageInfo(naverMapPackageName, PackageManager.PackageInfoFlags.of(0))
                    true
                } else {
                    @Suppress("DEPRECATION")
                    val pm = context.packageManager
                    pm.getPackageInfo(naverMapPackageName, 0)
                    true
                }
            } catch (e: PackageManager.NameNotFoundException) {
                Timber.e(e)
                false
            }
            if (isInstallNaverMap) {
                //slat=${myLocation.latitude}&slng=${myLocation.longitude}
                val url = "nmap://route/${if (isWalkDistance(
                        myLocation,
                        LatLng(sellLottoMarker.latitude!!, sellLottoMarker.longitude!!)
                    )) "walk" else "car"}?&dlat=${sellLottoMarker.latitude}&dlng=${sellLottoMarker.longitude}&dname=${sellLottoMarker.name}"
                val intent =  Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            } else {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$naverMapPackageName")))
            }
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.map_fail_load) + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDistanceToMeter(myLocation: LatLng, selectedLocation: LatLng): Float {
        val myLocationObj = Location("").apply {
            latitude = myLocation.latitude
            longitude = myLocation.longitude
        }

        val selectedLocationObj = Location("").apply {
            latitude = selectedLocation.latitude
            longitude = selectedLocation.longitude
        }
        val distance = myLocationObj.distanceTo(selectedLocationObj)
        return distance
    }

    private fun isWalkDistance(myLocation: LatLng?, selectedLocation: LatLng): Boolean {
        if (myLocation == null) return false
        val targetKm = 2.0f
        val distance = getDistanceToMeter(myLocation, selectedLocation) / 1000f
        return distance <= targetKm
    }
}

data class MapState(
    val isLoading: Boolean = true,
    val markers: List<SellLottoMarkerModel> = emptyList()
)

sealed class MapSideEffect {

}