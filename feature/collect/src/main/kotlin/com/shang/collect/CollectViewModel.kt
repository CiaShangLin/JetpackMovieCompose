package com.shang.collect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.data.repository.MovieRepository
import com.shang.model.MovieListBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {

    val allMovieCollect = movieRepository.getAllMovieCollect()
        .map { movieCollectList ->
            CollectUiState.Success(movieCollectList)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CollectUiState.Loading,
        )

    fun toggleMovieCollectStatus(movieResult: MovieListBean.Result) {
        viewModelScope.launch(Dispatchers.IO) {
            if (movieResult.isCollect) {
                movieRepository.deleteMovie(movieResult)
            } else {
                movieRepository.insertMovie(movieResult)
            }
        }
    }
}
