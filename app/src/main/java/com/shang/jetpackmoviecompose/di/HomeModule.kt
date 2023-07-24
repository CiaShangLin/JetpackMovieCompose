package com.shang.jetpackmoviecompose.di

import com.shang.jetpackmoviecompose.api.MovieApi
import com.shang.jetpackmoviecompose.ui.home.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {
    @Singleton
    @Provides
    fun provideHomeRepository(movieApi: MovieApi) = HomeRepository(movieApi)
}