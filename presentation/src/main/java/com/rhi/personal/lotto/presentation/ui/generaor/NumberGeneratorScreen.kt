package com.rhi.personal.lotto.presentation.ui.generaor

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rhi.personal.lotto.presentation.R
import com.rhi.personal.lotto.presentation.ui.component.LottoPaperView
import org.orbitmvi.orbit.compose.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NumberGeneratorScreen(
    viewModel: NumberGeneratorViewModel = hiltViewModel(),
    finish: () -> Unit
) {
    val state = viewModel.collectAsState().value
    Surface {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.number_generator_title)) },
                    navigationIcon = {
                        IconButton(
                            onClick = { finish() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LottoPaperView(
                    modifier = Modifier
                        .weight(0.7f),
                    numbers = state.numberList
                )


                Column(
                    modifier = Modifier.weight(0.3f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onClick = viewModel::generateNumber
                    ) {
                        Text(text = stringResource(R.string.number_generator_button))
                    }
                    if (state.numberList.isNotEmpty()) {
                        Button(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            onClick = {}
                        ) {
                            Text(text = stringResource(R.string.number_generator_save))
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun NumberPreview() {
    NumberGeneratorScreen() {}
}