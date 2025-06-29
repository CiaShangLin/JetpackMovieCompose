package com.shang.network.retrofit

import com.shang.network.model.MovieGenreResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieApiService {

    @GET("genre/movie/list")
    suspend fun getMovieGenres(): Response<MovieGenreResponse>
}
