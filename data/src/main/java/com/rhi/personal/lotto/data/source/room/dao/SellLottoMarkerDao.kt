package com.rhi.personal.lotto.data.source.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rhi.personal.lotto.data.source.room.entity.SellLottoMarkerEntity

@Dao
interface SellLottoMarkerDao {

    @Query("SELECT * FROM sell_lotto_marker WHERE latitude BETWEEN :startLatitude AND :endLatitude AND longitude BETWEEN :startLongitude AND :endLongitude")
    suspend fun getSellLottoMarkerBounds(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double,
    ): List<SellLottoMarkerEntity>

    @Query("SELECT * FROM sell_lotto_marker")
    suspend fun getSellLottoMarkerAll(): List<SellLottoMarkerEntity>

    @Query("SELECT COUNT(*) FROM sell_lotto_marker WHERE date = :updateDate")
    suspend fun getSellLottoMarkerSize(updateDate: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSellLottoMarker(markers: List<SellLottoMarkerEntity>)

}