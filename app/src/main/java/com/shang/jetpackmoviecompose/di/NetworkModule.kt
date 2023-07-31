package com.shang.jetpackmoviecompose.di

import com.shang.jetpackmoviecompose.ui.home.HomeRepository
import com.shang.jetpackmoviecompose.api.AuthInterceptor
import com.shang.jetpackmoviecompose.api.ConfigurationApi
import com.shang.jetpackmoviecompose.api.LanguageInterceptor
import com.shang.jetpackmoviecompose.api.MovieApi
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: okhttp3.OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(): okhttp3.OkHttpClient {
        return okhttp3.OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .addInterceptor(AuthInterceptor())
            .addInterceptor(LanguageInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit) = retrofit.create(MovieApi::class.java)

    @Singleton
    @Provides
    fun provideConfigurationApi(retrofit: Retrofit) = retrofit.create(ConfigurationApi::class.java)
}

