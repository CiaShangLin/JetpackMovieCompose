package com.shang.jetpackmoviecompose.ui

import com.shang.model.ConfigurationBean

sealed interface MainUiState {
    data object Loading : MainUiState
    data class Error(val throwable: Throwable) : MainUiState
    data class Success(val data: ConfigurationBean) : MainUiState
}
