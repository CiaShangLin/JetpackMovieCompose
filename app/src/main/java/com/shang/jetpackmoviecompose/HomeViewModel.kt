package com.shang.jetpackmoviecompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.jetpackmovie.bean.MovieGenreBean
import com.shang.jetpackmoviecompose.api.ApiService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val flow = MutableSharedFlow<MovieGenreBean?>()

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            try {
                val data = ApiService.movieApi.getMovieGenres()
                flow.emit(data)
            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }
}