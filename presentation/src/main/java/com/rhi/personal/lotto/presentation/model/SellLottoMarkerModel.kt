package com.rhi.personal.lotto.presentation.model

import com.rhi.personal.lotto.domain.model.SellLottoMarker

data class SellLottoMarkerModel(
    val date: Int,
    val no: Int,
    val name: String,
    val address1: String,
    val address2: String,
    val longitude: Double?,
    val latitude: Double?,
    var distance: Float? = null,
    var distanceString: String? = null
) {
    fun toDomain(): SellLottoMarker = SellLottoMarker(
        date = date,
        no = no,
        name = name,
        address1 = address1,
        address2 = address2,
        longitude = longitude,
        latitude = latitude
    )
}

fun SellLottoMarker.toModel(): SellLottoMarkerModel = SellLottoMarkerModel(
    date = date,
    no = no,
    name = name,
    address1 = address1,
    address2 = address2,
    longitude = longitude,
    latitude = latitude
)