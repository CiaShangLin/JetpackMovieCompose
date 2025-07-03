package com.shang.jetpackmoviecompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.domain.usecase.GetConfigurationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getConfigurationUseCase: GetConfigurationUseCase,
) : ViewModel() {

    val configuration: StateFlow<MainUiState> = getConfigurationUseCase()
        .map { result ->
            result.fold(
                onSuccess = { MainUiState.Success(it) },
                onFailure = { MainUiState.Error(it) },
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainUiState.Loading,
        )
}
