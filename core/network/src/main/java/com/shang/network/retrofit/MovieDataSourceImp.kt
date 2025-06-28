package com.shang.network.retrofit

import com.shang.model.MovieGenreBean
import com.shang.network.model.MovieGenreResponse
import com.shang.network.model.asExternalModel
import kotlinx.serialization.json.Json

class MovieDataSourceImp(private val _movieApiService: MovieApiService) :
    MovieDataSource {
    override suspend fun getMovieGenres(): MovieGenreBean {
        val json = _movieApiService.getMovieGenres().body()?.string() ?: ""
        val bean: MovieGenreResponse = Json.Default.decodeFromString<MovieGenreResponse>(json)
        return bean.asExternalModel()
    }
}