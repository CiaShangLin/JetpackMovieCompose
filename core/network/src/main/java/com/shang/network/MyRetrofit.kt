package com.shang.network

import retrofit2.Retrofit

object MyRetrofit {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .build()

    val movieApiService: MovieApiService = retrofit.create(MovieApiService::class.java)
}
