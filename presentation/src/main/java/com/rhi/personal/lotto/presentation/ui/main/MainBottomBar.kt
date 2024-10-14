package com.rhi.personal.lotto.presentation.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainBottomBar(
    currentRoute: String,
    onClick: (newRoute: String) -> Unit
) {
    Column {
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val mainNavClass = MainRouter.MainNav::class
            mainNavClass.sealedSubclasses.forEach { mainNavRouter ->
                val router = mainNavRouter.objectInstance as MainRouter.MainNav
                MainBottomBarButton(
                    icon = painterResource(router.iconDrawableRes),
                    name = stringResource(router.title),
                    isSelected = currentRoute == router.route,
                    contentDescription = router.contentDescription,
                    onClick = { onClick(router.route) }
                )
            }
        }
    }
}

@Composable
private fun MainBottomBarButton(
    icon: Painter,
    name: String,
    isSelected: Boolean,
    contentDescription: String? = null,
//    textColor: Color = Color.White,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .height(60.dp)
            .alpha(if (isSelected) 1f else 0.5f)
            .padding(vertical = 5.dp),
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        ),
        contentPadding = PaddingValues(0.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f),
                painter = icon,
                contentDescription = contentDescription,

                )
            Text(
                text = name,
                modifier = Modifier.padding(top = 4.dp),
                style = TextStyle(
//                    color = textColor,
                    fontSize = 12.sp,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.9f),
                        offset = Offset(3f, 3f),
                        blurRadius = 4f
                    )
                )
            )
        }
    }
}