package com.shang.collect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
}
