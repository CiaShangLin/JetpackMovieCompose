package com.shang.moviedetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.data.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = MovieDetailViewModel.Factory::class)
class MovieDetailViewModel @AssistedInject constructor(
    private val movieRepository: MovieRepository,
    @Assisted private val movieId: Int,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int): MovieDetailViewModel
    }

    val movieDetail = movieRepository.getMovieDetail(movieId)
        .map {
            it.fold(
                onSuccess = {
                    MovieDetailUiState.Success(it)
                },
                onFailure = {
                    MovieDetailUiState.Error(it.message ?: "Unknown error")
                },
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MovieDetailUiState.Loading,
        )
}
