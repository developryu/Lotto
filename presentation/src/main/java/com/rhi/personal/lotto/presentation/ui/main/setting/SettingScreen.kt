package com.rhi.personal.lotto.presentation.ui.main.setting

import com.rhi.personal.lotto.presentation.R
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rhi.personal.lotto.presentation.BuildConfig

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var alarmSwitchState by remember { mutableStateOf(false) }
    var adAlarmSwitchState by remember { mutableStateOf(false) }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),

        ) {
            SettingTitle(stringResource(R.string.setting_main_title1))
            SettingSubTitle(stringResource(R.string.setting_sub_title_alarm))
            SettingItem(
                name = stringResource(R.string.setting_item_alarm_draw),
                onClick = { alarmSwitchState = !alarmSwitchState },
                switchState = alarmSwitchState,
                onSwitchChange = { alarmSwitchState = it }
            )
            SettingItem(
                name = stringResource(R.string.setting_item_alarm_ad),
                onClick = { adAlarmSwitchState = !adAlarmSwitchState },
                switchState = adAlarmSwitchState,
                onSwitchChange = { adAlarmSwitchState = it }
            )
            Spacer(modifier = Modifier.size(20.dp))
            SettingTitle(name = stringResource(R.string.setting_main_title2))
            SettingSubTitle(name = stringResource(R.string.setting_sub_title_privacy))
            SettingItem(
                name = stringResource(R.string.setting_item_privacy_policy),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://naver.com"))
                    context.startActivity(intent)
                }
            )
            SettingItem(
                name = stringResource(R.string.setting_item_service),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://naver.com"))
                    context.startActivity(intent)
                }
            )
            SettingItem(
                name = stringResource(R.string.setting_item_open_source),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://naver.com"))
                    context.startActivity(intent)
                }
            )
            SettingSubTitle(name = stringResource(R.string.setting_sub_title_version))
            SettingItem(
                name = stringResource(R.string.setting_item_version),
                contentText = "v${BuildConfig.VERSION_NAME}")

        }
    }
}

@Composable
private fun SettingTitle(
    name: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text(
            modifier = Modifier.padding(bottom = 5.dp),
            text = name,
            fontSize = 15.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Color.Gray
        )
    }
}

@Composable
private fun SettingSubTitle(
    name: String,
) {
    Text(
        modifier = Modifier.padding(vertical = 5.dp).padding(start = 5.dp),
        text = name,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun SettingItem(
    name: String,
    onClick: (() -> Unit)? = null,
    switchState: Boolean? = null,
    onSwitchChange: ((Boolean) -> Unit)? = null,
    contentText: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .let { if (onClick != null) it.clickable { onClick() } else it }
            .padding(vertical = 10.dp)
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = name,
            fontSize = 14.sp
        )
        if (switchState != null && onSwitchChange != null) {
            Switch(
                modifier = Modifier.scale(0.75f).height(10.dp),
                checked = switchState,
                onCheckedChange = onSwitchChange
            )
        } else if (contentText != null) {
            Text(
                text = contentText
            )
        } else if (onClick != null) {
            Text(
                text = ">"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    SettingScreen()
}