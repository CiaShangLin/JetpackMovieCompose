package com.shang.network.di

import com.shang.network.retrofit.MovieDataSource
import com.shang.network.retrofit.MovieDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindMovieDataSource(
        movieDataSourceImp: MovieDataSourceImp,
    ): MovieDataSource
}
