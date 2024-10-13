package com.rhi.personal.lotto.data.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rhi.personal.lotto.data.source.room.dao.LottoDrawResultDao
import com.rhi.personal.lotto.data.source.room.entity.LottoDrawResultEntity

@Database(
    entities = [
        LottoDrawResultEntity::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        const val DB_NAME = "applicationDatabase.db"
    }

    abstract fun lottoDrawResultDao(): LottoDrawResultDao
}