package com.shang.jetpackmoviecompose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shang.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovieGenres() {
        viewModelScope.launch {
            val genre = movieRepository.getMovieGenres()
            Log.d("DEBUG", genre.toString())
        }
    }
}
