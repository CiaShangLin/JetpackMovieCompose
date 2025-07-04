package com.shang.home.ui

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.shang.data.repository.MovieRepository
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

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
        .stateIn(
            scope = CoroutineScope(kotlinx.coroutines.Dispatchers.Default),
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PagingData.empty(),
        )
}
