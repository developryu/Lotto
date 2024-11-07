package com.rhi.personal.lotto.presentation.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rhi.personal.lotto.presentation.model.SplashPreLoadModel
import com.rhi.personal.lotto.presentation.theme.LottoTheme
import com.rhi.personal.lotto.presentation.ui.qrscan.QrScanActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val SPLASH_PRE_LOAD_DATA = "splash_pre_load_data"
        const val KEY_DESTINATION = "destination"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        var preLoadModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(SPLASH_PRE_LOAD_DATA, SplashPreLoadModel::class.java)
        } else {
            intent.getParcelableExtra<SplashPreLoadModel>(SPLASH_PRE_LOAD_DATA)
        }

        setContent {
            LottoTheme {
                MainNavHost(preLoadModel)
            }
        }

        when(intent.extras?.getString(KEY_DESTINATION)) {
            QrScanActivity::class.java.simpleName -> {
                startActivity(Intent(this, QrScanActivity::class.java))
            }
        }
    }
}