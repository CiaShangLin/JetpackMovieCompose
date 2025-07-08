package com.shang.collect

import com.shang.model.MovieCardResult

sealed interface CollectUiState {
    data object Loading : CollectUiState

    data class Success(
        val movieCollectList: List<MovieCardResult>,
    ) : CollectUiState

    data class Error(
        val message: String,
    ) : CollectUiState
}
