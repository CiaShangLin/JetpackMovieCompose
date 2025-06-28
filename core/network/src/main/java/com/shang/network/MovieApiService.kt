package com.shang.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("genre/movie/list")
    suspend fun getMovieGenres(@Query("api_key") api_key: String = "90b44d4486f4ba2b48dbc22e2099b38b"): Response<ResponseBody>
}
