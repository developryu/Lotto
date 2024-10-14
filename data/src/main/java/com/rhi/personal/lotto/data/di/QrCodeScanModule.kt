package com.rhi.personal.lotto.data.di

import com.rhi.personal.lotto.data.usecase.QrCodeScanUseCaseImpl
import com.rhi.personal.lotto.domain.usecase.QrCodeScanUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface QrCodeScanModule {
    @Binds
    fun bindQrCodeScanUseCase(qrCodeScanUseCaseImpl: QrCodeScanUseCaseImpl): QrCodeScanUseCase
}