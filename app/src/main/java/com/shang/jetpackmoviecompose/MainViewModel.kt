package com.shang.jetpackmoviecompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.common.UiState
import com.shang.data.repository.MovieRepository
import com.shang.model.MovieGenreBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    val data: StateFlow<MainUiState> = movieRepository.getMovieGenres(Dispatchers.IO)
        .map { uiState ->
            when (uiState) {
                is UiState.Error -> MainUiState.Error(uiState.error)
                UiState.Loading -> MainUiState.Loading
                is UiState.Success<MovieGenreBean> -> MainUiState.Success(data = uiState.data)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainUiState.Loading,
        )
}
