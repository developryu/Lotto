package com.rhi.personal.lotto.presentation.ui.splash

import com.rhi.personal.lotto.presentation.R
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rhi.personal.lotto.presentation.model.SplashPreLoadModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    finish: (preLoadModel: SplashPreLoadModel) -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.collectAsState().value
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SplashSideEffect.LoadDataSuccess -> finish(sideEffect.preLoadModel)
            is SplashSideEffect.LoadDataFail -> {
                Toast.makeText(context, "인터넷 상태 확인 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    SplashScreen(state.isLoading)
}

@Composable
private fun SplashScreen(isLoading: Boolean) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_loading))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 1f,
        isPlaying = isLoading
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Splash\nScreen",
            fontSize = 80.sp,
            lineHeight =80.sp
        )
        Spacer(modifier = Modifier.size(20.dp))
        LottieAnimation(
            modifier = Modifier.size(100.dp),
            composition = composition,
            progress = { progress },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(true)
}