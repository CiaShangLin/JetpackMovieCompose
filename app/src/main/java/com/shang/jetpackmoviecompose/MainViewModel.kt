package com.shang.jetpackmoviecompose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.data.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel(private val movieRepository: MovieRepository = MovieRepository()) : ViewModel() {

    fun getMovieGenres() {
        viewModelScope.launch {
            val genre = movieRepository.getMovieGenres()
            Log.d("DEBUG", genre.toString())
        }
    }
}
