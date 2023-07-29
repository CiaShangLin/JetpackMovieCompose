package com.shang.jetpackmoviecompose.ui.genre

import androidx.lifecycle.*
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val mGenreRepository: GenreRepository,
) : ViewModel() {

    private var mPage = 1
    private var mGenre: MovieGenreBean.Genre? = null

    private val mGenreSharedFlow = MutableSharedFlow<MovieListBean?>()
    val genreSharedFlow: SharedFlow<MovieListBean?> = mGenreSharedFlow

    fun setGenre(genre: MovieGenreBean.Genre) {
        mGenre = genre
        getMovieGenreDetail()
    }

    fun getGenre() = mGenre

    init {

    }

    private fun getMovieGenreDetail() {
        if (mGenre == null) {
            return
        }
        viewModelScope.launch {
            val data = mGenreRepository.getMovieGenreDetail(mGenre!!.name, mPage).getOrNull()
            mGenreSharedFlow.emit(data)
        }
    }
}
