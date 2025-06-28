package com.shang.network.retrofit

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface MovieApiService {

    @GET("genre/movie/list")
    suspend fun getMovieGenres(): Response<ResponseBody>
}
