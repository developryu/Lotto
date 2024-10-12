package com.rhi.personal.lotto.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rhi.personal.lotto.presentation.theme.LottoTheme
import com.rhi.personal.lotto.presentation.ui.main.MainActivity
import com.rhi.personal.lotto.presentation.ui.main.MainActivity.Companion.SPLASH_PRE_LOAD_DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LottoTheme {
                SplashScreen { preLoadModel ->
                    startActivity(Intent(this, MainActivity::class.java).apply {
                        putExtra(SPLASH_PRE_LOAD_DATA, preLoadModel)
                    })
                    finish()
                }
            }
        }
    }
}