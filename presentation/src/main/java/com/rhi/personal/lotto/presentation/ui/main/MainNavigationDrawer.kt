package com.rhi.personal.lotto.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MainNavigationDrawer(

) {
    ModalDrawerSheet(
        modifier = Modifier.fillMaxWidth(0.7f)
    ) {
        HeaderNavigationDrawer()
        NavigationDrawerItem(
            label = {
                Text(text = "테스트")
            },
            selected = false,
            onClick = {

            }
        )
        NavigationDrawerItem(
            label = {
                Text(text = "테스트2")
            },
            selected = false,
            onClick = {

            }
        )
        NavigationDrawerItem(
            label = {
                Text(text = "테스트3")
            },
            selected = false,
            onClick = {

            }
        )
    }
}

@Composable
fun HeaderNavigationDrawer() {
    Box(
        modifier = Modifier.fillMaxWidth()
            .aspectRatio(1f)
            .background(Color.Green)
    ) {

    }
}