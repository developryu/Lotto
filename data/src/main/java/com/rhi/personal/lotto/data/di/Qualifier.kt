package com.rhi.personal.lotto.data.di

import jakarta.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IsDebug

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LottoRetrofit