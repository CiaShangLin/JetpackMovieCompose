package com.shang.collect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.data.repository.MovieRepository
import com.shang.ui.MovieCardData
import com.shang.ui.asMovieCardResult
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

    fun toggleMovieCollectStatus(data: MovieCardData) {
        viewModelScope.launch(Dispatchers.IO) {
            if (data.movieCardIsCollect) {
                movieRepository.deleteMovieCollect(data.asMovieCardResult())
            } else {
                movieRepository.insertMovieCollect(data.asMovieCardResult())
            }
        }
    }
}
