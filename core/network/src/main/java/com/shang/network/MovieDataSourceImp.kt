package com.shang.network

import com.shang.model.MovieGenreBean
import kotlinx.serialization.json.Json

class MovieDataSourceImp(private val movieApiService: MovieApiService = MyRetrofit.movieApiService) :
    MovieDataSource {
    override suspend fun getMovieGenres(): MovieGenreBean {
        val json = movieApiService.getMovieGenres().body()?.string() ?: ""
        val bean: MovieGenreResponse = Json.decodeFromString<MovieGenreResponse>(json)
        return bean.asExternalModel()
    }
}
