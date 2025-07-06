package com.shang.collect

import com.shang.model.MovieListBean

sealed interface CollectUiState {
    data object Loading : CollectUiState

    data class Success(
        val movieCollectList: List<MovieListBean.Result>,
    ) : CollectUiState

    data class Error(
        val message: String,
    ) : CollectUiState
}
