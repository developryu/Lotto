package com.rhi.personal.lotto.data.source.room.converter

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import java.util.Date

class LottoDrawResultConverter {
    private val gson = GsonBuilder().create()

    @TypeConverter
    fun toDate(dateLong: Long): Date {
        return Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun fromList(list: List<Int>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(str: String): List<Int> {
        return gson.fromJson(str, Array<Int>::class.java).toList()
    }
}