package com.rhi.personal.lotto.data.source.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rhi.personal.lotto.data.source.room.entity.LottoDrawResultEntity

@Dao
interface LottoDrawResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLottoDrawResult(lottoDrawResult: LottoDrawResultEntity)

    @Query("SELECT * FROM lotto_draw_result WHERE drawRound = :round")
    suspend fun getLottoDrawResult(round: Int): LottoDrawResultEntity?

    @Query("SELECT * FROM lotto_draw_result WHERE drawRound IN (:roundList) ORDER by drawDate DESC")
    suspend fun getLottoDrawResultList(roundList: List<Int>): List<LottoDrawResultEntity>

}