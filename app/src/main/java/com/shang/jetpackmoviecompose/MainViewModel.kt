package com.shang.jetpackmoviecompose

import androidx.lifecycle.ViewModel
import com.shang.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    val data = movieRepository.getMovieGenres()
}
