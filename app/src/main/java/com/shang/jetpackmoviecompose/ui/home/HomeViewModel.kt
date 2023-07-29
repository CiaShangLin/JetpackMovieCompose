package com.shang.jetpackmoviecompose.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.jetpackmovie.bean.MovieGenreBean
import com.shang.jetpackmoviecompose.api.StatefulMutableLiveData
import com.shang.jetpackmoviecompose.api.UiState
import com.skydoves.sandwich.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject() constructor(
    private val mHomeRepository: HomeRepository
) : ViewModel() {


    private val mMovieGenreFlow = MutableSharedFlow<MovieGenreBean?>()
    val movieGenreFlow: SharedFlow<MovieGenreBean?> = mMovieGenreFlow


    init {
        getMovieGenre()
    }

    private fun getMovieGenre() {
        viewModelScope.launch {
            val data = mHomeRepository.getMovieGenres().getOrNull()
            mMovieGenreFlow.emit(data)
        }
    }
}