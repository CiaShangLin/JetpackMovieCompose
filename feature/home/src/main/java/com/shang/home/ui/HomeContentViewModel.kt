package com.shang.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.data.repository.MovieRepository
import com.shang.domain.usecase.GetHomeMovieListUseCase
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = HomeContentViewModel.Factory::class)
class HomeContentViewModel @AssistedInject constructor(
    private val movieRepository: MovieRepository,
    getMovieGenreUseCase: GetHomeMovieListUseCase,
    @Assisted val movieGenre: MovieGenreBean.MovieGenre,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(movieGenre: MovieGenreBean.MovieGenre): HomeContentViewModel
    }

    val movieList =
        getMovieGenreUseCase(movieGenre.id.toString(), viewModelScope)

    fun insertMovie(movie: MovieListBean.Result) {
        viewModelScope.launch {
            if (movie.isCollect) {
                movieRepository.deleteMovie(movie)
            } else {
                movieRepository.insertMovie(movie)
            }
        }
    }
}
