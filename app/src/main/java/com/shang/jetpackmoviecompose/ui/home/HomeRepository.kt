package com.shang.jetpackmoviecompose.ui.home

import com.shang.jetpackmovie.bean.MovieGenreBean
import com.shang.jetpackmoviecompose.api.MovieApi
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val mMovieApi: MovieApi
) {

    suspend fun getMovieGenres(): MovieGenreBean {
        return mMovieApi.getMovieGenres()
    }
}