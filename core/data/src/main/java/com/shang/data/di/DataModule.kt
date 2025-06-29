package com.shang.data.di

import com.shang.data.repository.MovieRepository
import com.shang.data.repository.MovieRepositoryImp
import com.shang.network.retrofit.MovieDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMovieRepository(movieDataSource: MovieDataSource): MovieRepository {
        return MovieRepositoryImp(movieDataSource)
    }
}
