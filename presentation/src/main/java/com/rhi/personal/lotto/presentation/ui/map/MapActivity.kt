package com.rhi.personal.lotto.presentation.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import com.rhi.personal.lotto.presentation.theme.LottoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LottoTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("내 주변 로또 판매점")
                            },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        finish()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        )
                    }
                ) {
                    MapScreen(
                        finish = {
                            finish()
                        }
                    )
                }
            }
        }
    }
}