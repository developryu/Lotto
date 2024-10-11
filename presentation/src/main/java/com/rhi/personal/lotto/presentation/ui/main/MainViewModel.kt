package com.rhi.personal.lotto.presentation.ui.main

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

class MainViewModel @Inject constructor(

) : ViewModel(), ContainerHost<MainState, MainSideEffect> {

    override val container: Container<MainState, MainSideEffect> = container(
        initialState = MainState()
    )

}

data class MainState(
    val test: Int = 1
)

sealed class MainSideEffect {

}
