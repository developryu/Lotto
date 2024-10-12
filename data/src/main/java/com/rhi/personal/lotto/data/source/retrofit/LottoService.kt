package com.rhi.personal.lotto.data.source.retrofit

import com.rhi.personal.lotto.data.model.LottoApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LottoService {
    @GET("common.do?method=getLottoNumber")
    suspend fun getLottoDrawResult(
        @Query("drwNo") round: Int
    ) : LottoApiResponse
}