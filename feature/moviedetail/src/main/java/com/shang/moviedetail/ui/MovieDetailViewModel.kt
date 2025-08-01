package com.shang.moviedetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.common.UiState
import com.shang.data.repository.MovieRepository
import com.shang.domain.usecase.GetMovieDetailUseCase
import com.shang.domain.usecase.GetMovieRecommendUseCase
import com.shang.model.asMovieCardResult
import com.shang.ui.MovieCardData
import com.shang.ui.asMovieCardResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MovieDetailViewModel.Factory::class)
class MovieDetailViewModel @AssistedInject constructor(
    private val movieRepository: MovieRepository,
    getMovieDetailUseCase: GetMovieDetailUseCase,
    getMovieRecommendUseCase: GetMovieRecommendUseCase,
    @Assisted private val movieId: Int,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(movieId: Int): MovieDetailViewModel
    }

    private val _retryTrigger = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val movieDetail =
        _retryTrigger.flatMapLatest {
            getMovieDetailUseCase(movieId)
                .map {
                    it.fold(
                        onSuccess = {
                            MovieDetailUiState.Success(it)
                        },
                        onFailure = {
                            MovieDetailUiState.Error(it)
                        },
                    )
                }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = MovieDetailUiState.Loading,
            )

    val movieCollect = movieRepository.getMovieCollectEntityById(movieId)
        .map {
            it?.isCollect ?: false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false,
        )

    val movieRecommendations = getMovieRecommendUseCase(movieId)
        .map {
            it.fold(
                onSuccess = {
                    UiState.Success(it)
                },
                onFailure = {
                    UiState.Error(it)
                },
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading,
        )

    val movieActors = movieRepository.getMovieActor(movieId)
        .map {
            it.fold(
                onSuccess = { UiState.Success(it.cast) },
                onFailure = { UiState.Error(it) },
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading,
        )

    fun toggleCollect(data: MovieCardData, isCollect: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isCollect) {
                movieRepository.deleteMovieCollect(data.asMovieCardResult())
            } else {
                movieRepository.insertMovieCollect(data.asMovieCardResult())
            }
        }
    }

    fun retryMovieDetailApi() {
        viewModelScope.launch(Dispatchers.IO) {
            _retryTrigger.emit(_retryTrigger.value + 1)
        }
    }
}
