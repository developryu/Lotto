package com.rhi.personal.lotto.domain.model

data class SellLottoMarker(
    val date: Int,
    val no: Int,
    val name: String,
    val address1: String,
    val address2: String,
    val longitude: Double?,
    val latitude: Double?
)
