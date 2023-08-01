package com.shang.jetpackmoviecompose.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.jetpackmovie.bean.MovieGenreBean
import com.skydoves.sandwich.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject() constructor(
    private val mHomeRepository: HomeRepository
) : ViewModel() {


    private val mMovieGenreLiveData = MutableLiveData<MovieGenreBean?>()
    val movieGenreLiveData: LiveData<MovieGenreBean?> = mMovieGenreLiveData


    init {
        getMovieGenre()
    }

    private fun getMovieGenre() {
        viewModelScope.launch {
            val data = mHomeRepository.getMovieGenres().getOrNull()
            mMovieGenreLiveData.value = data
        }
    }
}