package com.shang.moviedetail.ui

import com.shang.model.MovieDetailBean

sealed interface MovieDetailUiState {
    data object Loading : MovieDetailUiState

    data class Success(val data: MovieDetailBean) : MovieDetailUiState

    data class Error(val message: String) : MovieDetailUiState
}
