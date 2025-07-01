package com.shang.jetpackmoviecompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.common.UiState
import com.shang.data.repository.MovieRepository
import com.shang.data.repository.UserDataRepository
import com.shang.model.MovieBean
import com.shang.model.MovieGenreBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val userDataRepository: UserDataRepository,
) :
    ViewModel() {

    val userData = userDataRepository.userData.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null,
    )

    val data: StateFlow<MainUiState> = movieRepository.getMovieGenres()
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

    val dbData: StateFlow<List<MovieBean>> = movieRepository.getDatabaseMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )

    fun insertMovie() {
        val movieBean = MovieBean(
            id = 1,
            title = "Sample Movie",
            posterPath = "/sample_poster.jpg",
            releaseDate = "2023-01-01",
            voteAverage = 8.5,
        )
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.insertMovie(movieBean)
        }
    }

    fun setUserDataShow() {
        viewModelScope.launch(Dispatchers.IO) {
            val showCompleted = userData.value?.showCompleted ?: false
            userDataRepository.setShowCompleted(!showCompleted)
        }
    }

    fun setUserDataVersion() {
        viewModelScope.launch(Dispatchers.IO) {
            val version = userData.value?.version ?: 1
            userDataRepository.setVersion(version + 1)
        }
    }
}
