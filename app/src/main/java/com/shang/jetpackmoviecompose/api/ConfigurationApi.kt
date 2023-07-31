package com.shang.jetpackmoviecompose.api

import com.shang.jetpackmovie.bean.ConfigurationBean
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface ConfigurationApi {
    @GET("configuration")
    suspend fun getConfiguration(): ApiResponse<ConfigurationBean>
}