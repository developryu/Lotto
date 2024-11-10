package com.rhi.personal.lotto.presentation.ui.map

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
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
import com.naver.maps.map.overlay.OverlayImage
import com.rhi.personal.lotto.presentation.R
import com.rhi.personal.lotto.presentation.model.SellLottoMarkerModel
import com.rhi.personal.lotto.presentation.ui.permission.PermissionDialog
import com.rhi.personal.lotto.presentation.ui.permission.Permissions
import kotlinx.coroutines.flow.distinctUntilChanged
import org.orbitmvi.orbit.compose.collectAsState

// https://github.com/fornewid/naver-map-compose
// https://velog.io/@abh0920one/Compose-BottomSheet%EC%97%90-%EB%84%A4%EC%9D%B4%EB%B2%84-%EC%A7%80%EB%8F%84-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0
// 가까운 로또 1등 판매점 찾아보기 - https://redballs.tistory.com/entry/%EA%B0%80%EA%B9%8C%EC%9A%B4-%EB%A1%9C%EB%98%90-1%EB%93%B1-%ED%8C%90%EB%A7%A4%EC%A0%90-%EC%B0%BE%EC%95%84%EB%B3%B4%EA%B8%B0
@OptIn(ExperimentalNaverMapApi::class)
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
    var selectedMarker by remember { mutableStateOf<SellLottoMarkerModel?>(null) }



    var myLocation by remember { mutableStateOf(LatLng(37.5666102, 126.9783881)) }



    if (isShowPermissionDialog) {
        PermissionDialog(
            permissions = listOf(Permissions.ACCESS_COARSE_LOCATION, Permissions.ACCESS_FINE_LOCATION),
            functionName = stringResource(R.string.permission_function_map),
            onGranted = { isShowPermissionDialog = false },
            onDenied = finish
        )
    } else {
        if (!state.isLoading) {
            MapScreen(
                cameraPositionState = cameraPositionState,
                markers = state.markers,
                onLocationChange = { myLocation = it },
                onClickMarker = {
                    selectedMarker = it
                }
            )
            MarkerDialog(
                sellLottoMarker = selectedMarker,
                myLocation = myLocation,
                onDismissRequest = { selectedMarker = null }
            )
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
    markers: List<SellLottoMarkerModel> = emptyList(),
    onClickMarker: (SellLottoMarkerModel) -> Unit = {},
    onLocationChange: (LatLng) -> Unit
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
            onLocationChange = { location ->
                onLocationChange(LatLng(location.latitude, location.longitude))
            }
        ) {
            markers.forEach {
                if (it.latitude != null && it.longitude != null) {
                    Marker(
                        width = 20.dp,
                        height = 20.dp,
                        icon = OverlayImage.fromResource(R.drawable.ic_shop),
                        state = MarkerState(position = LatLng(it.latitude, it.longitude)),
                        captionText = it.name,
                        onClick = { marker ->
                            onClickMarker(it)
                            true
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MarkerDialog(
    sellLottoMarker: SellLottoMarkerModel?,
    myLocation: LatLng,
    onDismissRequest: () -> Unit
) {
    if (sellLottoMarker == null) return
    val context = LocalContext.current
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        Card(
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
                    .background(
                        color = Color.White,
                    )
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Text(
                    text = sellLottoMarker.name
                )

                Row(
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "도로명",
                        fontSize = 10.sp,
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 4.dp)
                    )
                    Text(
                        text = sellLottoMarker.address1,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                Row(
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "지번",
                        fontSize = 10.sp,
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 4.dp)
                    )
                    Text(
                        text = sellLottoMarker.address2,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {  }
                    ) {
                        Text(
                            text = stringResource(R.string.share)
                        )
                    }
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            try {
                                val url = "nmap://route/${if (isWalkDistance(
                                    myLocation,
                                    LatLng(sellLottoMarker.latitude!!, sellLottoMarker.longitude!!)
                                )) "walk" else "car"}?slat=${myLocation.latitude}&slng=${myLocation.longitude}&dlat=${sellLottoMarker.latitude}&dlng=${sellLottoMarker.longitude}&dname=${sellLottoMarker.name}"
                                val intent =  Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                                val installCheck = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    context.packageManager.queryIntentActivities(
                                        Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER),
                                        PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
                                    )
                                } else {
                                    context.packageManager.queryIntentActivities(
                                        Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER),
                                        PackageManager.GET_META_DATA
                                    )
                                }

                                if (installCheck.isEmpty()) {
                                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.nhn.android.nmap")))
                                } else {
                                    context.startActivity(intent)
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "지도 앱을 열 수 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                            onDismissRequest()
                        }
                    ) {
                        Text(
                            text = "길찾기"
                        )
                    }
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = onDismissRequest
                    ) {
                        Text(
                            text = stringResource(R.string.close)
                        )
                    }
                }
            }
        }
    }
}

fun isWalkDistance(myLocation: LatLng, selectedLocation: LatLng): Boolean {
    val targetKm = 2.0
    val myLocationObj = Location("").apply {
        latitude = myLocation.latitude
        longitude = myLocation.longitude
    }

    val selectedLocationObj = Location("").apply {
        latitude = selectedLocation.latitude
        longitude = selectedLocation.longitude
    }
    val distance = myLocationObj.distanceTo(selectedLocationObj)
    return distance <= (targetKm * 1000)
}