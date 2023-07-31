package com.shang.jetpackmoviecompose.ui.genre

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.shang.jetpackmovie.bean.MovieGenreBean
import com.shang.jetpackmovie.bean.MovieListBean
import com.shang.jetpackmoviecompose.MainViewModel
import com.skydoves.sandwich.getOrNull
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val mGenreRepository: GenreRepository,
) : ViewModel() {


    private var mGenre: MovieGenreBean.Genre? = null


    fun setGenre(genre: MovieGenreBean.Genre) {
        mGenre = genre
    }

    fun getGenre() = mGenre

    fun getMovieGenreDetail() =
        mGenreRepository
            .getMovieGenreDetail("${mGenre?.id ?: -1}")
            .cachedIn(viewModelScope)

}
