package com.shang.data.di

import com.shang.data.repository.MovieRepository
import com.shang.data.repository.MovieRepositoryImp
import com.shang.data.repository.UserDataRepository
import com.shang.data.repository.UserDataRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryImp: MovieRepositoryImp,
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindUserDataRepository(
        userDataRepositoryImp: UserDataRepositoryImp,
    ): UserDataRepository
}
