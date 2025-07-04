package com.shang.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shang.data.repository.MovieRepository
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

@HiltViewModel(assistedFactory = HomeContentViewModel.Factory::class)
class HomeContentViewModel @AssistedInject constructor(
    private val movieRepository: MovieRepository,
    @Assisted val movieGenre: MovieGenreBean.MovieGenre,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(movieGenre: MovieGenreBean.MovieGenre): HomeContentViewModel
    }

    val movieList: Flow<PagingData<MovieListBean.Result>> = movieRepository.getMovieGenrePager(movieGenre.id.toString())
        .cachedIn(viewModelScope)
}
