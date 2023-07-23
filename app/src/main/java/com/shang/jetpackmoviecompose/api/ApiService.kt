package com.shang.jetpackmoviecompose.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


object ApiService  {
    private val mOkHttpClient =  OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .addInterceptor(AuthInterceptor())
        .addInterceptor(LanguageInterceptor())
        .build()

    private val mRetrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(mOkHttpClient)
        .build()



    val movieApi by lazy {
        mRetrofit.create(MovieApi::class.java)
    }
}