package com.rhi.personal.lotto.data.di

import android.content.Context
import androidx.room.Room
import com.rhi.personal.lotto.data.source.room.AppDatabase
import com.rhi.personal.lotto.data.source.room.dao.LottoDrawResultDao
import com.rhi.personal.lotto.data.source.room.dao.SellLottoMarkerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            AppDatabase.DB_NAME,

        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideLottoDao(database: AppDatabase): LottoDrawResultDao {
        return database.lottoDrawResultDao()
    }

    @Provides
    @Singleton
    fun provideSellLottoMarker(database: AppDatabase): SellLottoMarkerDao {
        return database.sellLottoMarkerDao()
    }
}