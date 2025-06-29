package com.shang.network.retrofit

import com.shang.network.model.MovieGenreResponse
import javax.inject.Inject

class MovieDataSourceImp @Inject constructor(private val _movieApiService: MovieApiService) :
    MovieDataSource {
    override suspend fun getMovieGenres(): MovieGenreResponse {
        return _movieApiService.getMovieGenres().body()!!
    }
}
