package com.rhi.personal.lotto.presentation.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.rhi.personal.lotto.presentation.R
import com.rhi.personal.lotto.presentation.model.SellLottoMarkerModel
import com.rhi.personal.lotto.presentation.ui.permission.PermissionDialog
import com.rhi.personal.lotto.presentation.ui.permission.Permissions
import kotlinx.coroutines.flow.distinctUntilChanged
import org.orbitmvi.orbit.compose.collectAsState
import timber.log.Timber

// https://github.com/fornewid/naver-map-compose
// https://velog.io/@abh0920one/Compose-BottomSheet%EC%97%90-%EB%84%A4%EC%9D%B4%EB%B2%84-%EC%A7%80%EB%8F%84-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0
// 가까운 로또 1등 판매점 찾아보기 - https://redballs.tistory.com/entry/%EA%B0%80%EA%B9%8C%EC%9A%B4-%EB%A1%9C%EB%98%90-1%EB%93%B1-%ED%8C%90%EB%A7%A4%EC%A0%90-%EC%B0%BE%EC%95%84%EB%B3%B4%EA%B8%B0
@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel(),
    finish: () -> Unit
) {
    val state = viewModel.collectAsState().value
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(37.5666102, 126.9783881), 15.0)
    }
    var isShowPermissionDialog by remember { mutableStateOf(true) }
    if (isShowPermissionDialog) {
        PermissionDialog(
            permissions = listOf(Permissions.ACCESS_COARSE_LOCATION, Permissions.ACCESS_FINE_LOCATION),
            functionName = stringResource(R.string.permission_function_map),
            onGranted = { isShowPermissionDialog = false },
            onDenied = finish
        )
    } else {
        if (!state.isLoading) {
            MapScreen(cameraPositionState, state.markers)
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Loading...")
            }
        }
    }

    LaunchedEffect(cameraPositionState) {
        if (state.isLoading) {
            viewModel.initMarkerData()
        }
        snapshotFlow { cameraPositionState.position }
            .distinctUntilChanged()
            .collect {
                cameraPositionState.contentBounds?.let { bounds ->
                    viewModel.getMarkers(bounds)
                }
            }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
private fun MapScreen(
    cameraPositionState: CameraPositionState,
    markers: List<SellLottoMarkerModel> = emptyList()
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NaverMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier.fillMaxSize(),
            locationSource = rememberFusedLocationSource(),
            properties = MapProperties(
                locationTrackingMode = LocationTrackingMode.Follow
            ),
            uiSettings = MapUiSettings(
                isLocationButtonEnabled = true
            ),

        ) {
            markers.forEach {
                if (it.latitude != null && it.longitude != null) {
                    Marker(
                        state = MarkerState(position = LatLng(it.latitude, it.longitude)),
                        captionText = it.name
                    )
                }
            }
        }
    }
}