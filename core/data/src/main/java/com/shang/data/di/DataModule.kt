package com.shang.data.di

import com.shang.common.di.CommonDispatcher
import com.shang.common.di.Dispatcher
import com.shang.data.repository.MovieRepository
import com.shang.data.repository.MovieRepositoryImp
import com.shang.network.retrofit.MovieDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieDataSource: MovieDataSource,
        @Dispatcher(CommonDispatcher.IO) dispatcher: CoroutineDispatcher,
    ): MovieRepository {
        return MovieRepositoryImp(movieDataSource, dispatcher)
    }
}
