package com.shang.data.repository

import com.shang.model.MovieGenreBean
import com.shang.network.retrofit.MovieDataSource
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val _movieDataSource: MovieDataSource) : MovieRepository {
    override suspend fun getMovieGenres(): MovieGenreBean {
        return _movieDataSource.getMovieGenres()
    }
 }
