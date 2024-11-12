package com.rhi.personal.lotto.presentation.ui.generaor

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class NumberGeneratorViewModel @Inject constructor(

) : ViewModel(), ContainerHost<NumberGeneratorState, NumberGeneratorSideEffect> {

    override val container: Container<NumberGeneratorState, NumberGeneratorSideEffect> = container(
        initialState = NumberGeneratorState()
    )

    fun generateNumber() = intent {
        val numbers = (1..45).shuffled().take(6).sorted()
        reduce {
            state.copy(
                numberList = numbers
            )
        }
    }
}

data class NumberGeneratorState(
    val numberList: List<Int> = emptyList()
)

sealed class NumberGeneratorSideEffect {

}