package com.rhi.personal.lotto.presentation.ui.main

import com.rhi.personal.lotto.presentation.R
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.rhi.personal.lotto.presentation.ui.generaor.NumberGeneratorActivity
import com.rhi.personal.lotto.presentation.ui.history.LottoResultHistoryActivity
import com.rhi.personal.lotto.presentation.ui.map.MapActivity
import com.rhi.personal.lotto.presentation.ui.qrscan.QrScanActivity

@Composable
fun MainNavigationDrawer(

) {
    val context = LocalContext.current
    ModalDrawerSheet(
        modifier = Modifier.fillMaxWidth(0.7f)
    ) {
        HeaderNavigationDrawer()
        NavigationDrawerItem(
            label = {
                Text(text = stringResource(R.string.navi_drawer_title_qr_scan))
            },
            selected = false,
            onClick = {
                val intent = Intent(context, QrScanActivity::class.java)
                context.startActivity(intent)
            }
        )

        NavigationDrawerItem(
            label = {
                Text(text = stringResource(R.string.navi_drawer_title_map))
            },
            selected = false,
            onClick = {
                val intent = Intent(context, MapActivity::class.java)
                context.startActivity(intent)
            }
        )
        NavigationDrawerItem(
            label = {
                Text(text = stringResource(R.string.navi_drawer_title_lotto_result_histroy))
            },
            selected = false,
            onClick = {
                val intent = Intent(context, LottoResultHistoryActivity::class.java)
                context.startActivity(intent)
            }
        )
        NavigationDrawerItem(
            label = {
                Text(text = stringResource(R.string.navi_drawer_title_number_generator))
            },
            selected = false,
            onClick = {
                val intent = Intent(context, NumberGeneratorActivity::class.java)
                context.startActivity(intent)
            }
        )
    }
}

@Composable
fun HeaderNavigationDrawer() {
    Box(
        modifier = Modifier.fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.ic_nav_header),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}