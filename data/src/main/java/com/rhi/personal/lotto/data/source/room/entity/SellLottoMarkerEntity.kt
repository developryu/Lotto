package com.rhi.personal.lotto.data.source.room.entity

import androidx.room.Entity
import com.rhi.personal.lotto.domain.model.SellLottoMarker

@Entity(tableName = "sell_lotto_marker", primaryKeys = ["date", "no"])
data class SellLottoMarkerEntity(
    val date: Int,
    val no: Int,
    val name: String,
    val address1: String,
    val address2: String,
    val longitude: Double?,
    val latitude: Double?
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

fun SellLottoMarker.toEntity(): SellLottoMarkerEntity = SellLottoMarkerEntity(
    date = date,
    no = no,
    name = name,
    address1 = address1,
    address2 = address2,
    longitude = longitude,
    latitude = latitude
)