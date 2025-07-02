package com.shang.network.retrofit

import com.shang.network.model.ConfigurationResponse
import com.shang.network.model.MovieGenreResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieApiService {

    /**
     * 獲取配置
     */
    @GET("configuration")
    suspend fun getConfiguration(): Response<ConfigurationResponse>

    /**
     * 首頁電影類型標籤
     */
    @GET("genre/movie/list")
    suspend fun getMovieGenres(): Response<MovieGenreResponse>
}
