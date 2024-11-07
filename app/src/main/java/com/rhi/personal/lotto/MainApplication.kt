package com.rhi.personal.lotto

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
            Timber.plant(object : Timber.DebugTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, "$tag - Hoil", message, t)
                }
            })
        }
    }

    @Inject
    lateinit var workFactory: HiltWorkerFactory

    @Inject
    lateinit var configuration: Configuration

    override val workManagerConfiguration: Configuration
        get() = configuration
}