package com.shang.home.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.shang.data.repository.MovieRepository
import com.shang.model.MovieGenreBean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeContentViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val KEY_MOVIES = "KEY_MOVIES"
    }

    val movieGenres = savedStateHandle.getStateFlow<Int>(
        KEY_MOVIES,
        0,
    )

    fun setMovieGenre(movieGenre: MovieGenreBean.MovieGenre) {
        savedStateHandle[KEY_MOVIES] = movieGenre.id
    }

    init {
    }
}
