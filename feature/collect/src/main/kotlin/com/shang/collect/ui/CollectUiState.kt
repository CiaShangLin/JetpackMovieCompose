package com.shang.collect.ui

import com.shang.model.MovieCardResult

sealed interface CollectUiState {
    data object Empty : CollectUiState

    data class Success(
        val movieCollectList: List<MovieCardResult>,
    ) : CollectUiState
}
