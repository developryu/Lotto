package com.rhi.personal.lotto.data.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rhi.personal.lotto.data.source.room.dao.LottoDrawResultDao
import com.rhi.personal.lotto.data.source.room.dao.SellLottoMarkerDao
import com.rhi.personal.lotto.data.source.room.entity.LottoDrawResultEntity
import com.rhi.personal.lotto.data.source.room.entity.SellLottoMarkerEntity

@Database(
    entities = [
        LottoDrawResultEntity::class,
        SellLottoMarkerEntity::class
    ],
    version = 2
)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val DB_NAME = "applicationDatabase.db"
    }

    abstract fun lottoDrawResultDao(): LottoDrawResultDao
    abstract fun sellLottoMarkerDao(): SellLottoMarkerDao
}