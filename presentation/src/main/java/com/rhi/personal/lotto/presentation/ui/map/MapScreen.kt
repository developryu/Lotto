package com.rhi.personal.lotto.presentation.ui.map

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    val context = LocalContext.current
    val state = viewModel.collectAsState().value
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(37.5666102, 126.9783881), 15.0)
    }
    var isShowPermissionDialog by remember { mutableStateOf(true) }
    var selectedMarker by remember { mutableStateOf<SellLottoMarkerModel?>(null) }
    var myLocation by remember { mutableStateOf<LatLng?>(null) }

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
                onDismissRequest = { selectedMarker = null },
                onClickNaverMap = {
                    selectedMarker?.let {
                        viewModel.openNaverMap(context, myLocation, it)
                    }
                }
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.map_loading))
            }
        }
    }

    LaunchedEffect(cameraPositionState, myLocation) {
        if (state.isLoading) {
            viewModel.initMarkerData()
        }
        snapshotFlow { cameraPositionState.position }
            .distinctUntilChanged()
            .collect {
                cameraPositionState.contentBounds?.let { bounds ->
                    viewModel.getMarkers(bounds, myLocation)
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
    var showBottomSheet by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
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
            if (markers.isNotEmpty()) {
                Button(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    onClick = { showBottomSheet = true }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(15.dp),
                            imageVector = Icons.Default.Menu,
                            contentDescription = "list",
                            tint = Color.White,
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.map_sell_market_list),
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }

                if (showBottomSheet) {
                    MarkerListBottomSheet(
                        markers = markers,
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        onClickMarket = {
                            cameraPositionState.position = CameraPosition(
                                LatLng(it.latitude ?: 0.0, it.longitude ?: 0.0),
                                15.0
                            )
                            showBottomSheet = false
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
    onDismissRequest: () -> Unit,
    onClickNaverMap: () -> Unit
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 4.dp),
                            text = stringResource(R.string.map_address1),
                            fontSize = 8.sp,
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
                            text = stringResource(R.string.map_address2),
                            fontSize = 8.sp,
                            modifier = Modifier
                                .wrapContentSize()
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

                    if (sellLottoMarker.distanceString != null) {
                        Text(
                            text = String.format(context.getString(R.string.map_distance), sellLottoMarker.distanceString),
                            fontSize = 10.sp,
                        )
                    }
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
                        onClick = {
                            val text = String.format(
                                context.getString(R.string.share_map_text),
                                sellLottoMarker.name,
                                sellLottoMarker.address1,
                                sellLottoMarker.address2
                            )
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, text)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.share)
                        )
                    }
                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onClickNaverMap()
                            onDismissRequest()
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.map_find_load)
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




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkerListBottomSheet(
    markers: List<SellLottoMarkerModel>,
    onDismissRequest: () -> Unit,
    onClickMarket: (SellLottoMarkerModel) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 20.dp),
            text = stringResource(R.string.map_sell_market_list),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.3f)
        ) {
            items(
                count = markers.size
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClickMarket(markers[it]) }
                        .padding(4.dp)

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = markers[it].name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = markers[it].address1,
                                fontSize = 14.sp,
                            )
                            Text(
                                text = markers[it].address2,
                                fontSize = 14.sp,
                            )
                        }
                        Text(
                            text = markers[it].distanceString ?: "",
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                }
            }
        }
    }
}