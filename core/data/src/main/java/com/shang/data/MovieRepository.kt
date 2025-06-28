package com.shang.data

import com.shang.model.MovieGenreBean
import com.shang.network.MovieDataSource
import com.shang.network.MovieDataSourceImp

class MovieRepository(private val movieDataSource: MovieDataSource = MovieDataSourceImp()) {

    suspend fun getMovieGenres(): MovieGenreBean {
        return movieDataSource.getMovieGenres()
    }
}
