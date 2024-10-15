package com.rhi.personal.lotto.presentation.ui.permission

import androidx.lifecycle.ViewModel
import com.rhi.personal.lotto.domain.usecase.PermissionUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel(assistedFactory = PermissionViewModel.PermissionViewModelFactory::class)
class PermissionViewModel @AssistedInject constructor(
    @Assisted private val permissions: List<Permissions>,
    private val permissionUseCase: PermissionUseCase
) : ViewModel(), ContainerHost<PermissionState, PermissionSideEffect> {

    override val container: Container<PermissionState, PermissionSideEffect> = container(
        initialState = PermissionState()
    )

    @AssistedFactory
    interface PermissionViewModelFactory {
        fun create(permissions: List<Permissions>): PermissionViewModel
    }

    init {
        checkIsShouldShowRationale()
    }

    private fun checkIsShouldShowRationale() = intent {
        val result = permissionUseCase.getPermissionIsShouldShowRationale(permissions.map { it.permission }).getOrNull() == true
        reduce {
            state.copy(isShouldShowRationale = result)
        }
    }

    fun setShouldShowRationale() = intent {
        permissionUseCase.setPermissionIsShouldShowRationale(permissions.map { it.permission }, true)
        reduce {
            state.copy(isShouldShowRationale = true)
        }
    }
}

data class PermissionState(
    val isShouldShowRationale: Boolean = false
)

sealed class PermissionSideEffect