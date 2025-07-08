package com.shang.domain.di

import com.shang.common.di.CommonDispatcher
import com.shang.common.di.Dispatcher
import com.shang.data.repository.MovieRepository
import com.shang.data.repository.UserDataRepository
import com.shang.domain.usecase.GetConfigurationUseCase
import com.shang.domain.usecase.GetHomeMovieListUseCase
import com.shang.domain.usecase.GetMovieDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetConfigurationUseCase(
        movieRepository: MovieRepository,
        userDataRepository: UserDataRepository,
        @Dispatcher(CommonDispatcher.IO) ioDispatcher: CoroutineDispatcher,
    ): GetConfigurationUseCase {
        return GetConfigurationUseCase(movieRepository, userDataRepository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideGetHomeMovieListUseCase(
        movieRepository: MovieRepository,
        @Dispatcher(CommonDispatcher.IO) ioDispatcher: CoroutineDispatcher,
    ): GetHomeMovieListUseCase {
        return GetHomeMovieListUseCase(movieRepository, ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideGetMovieDetailUseCase(
        movieRepository: MovieRepository,
        @Dispatcher(CommonDispatcher.IO) ioDispatcher: CoroutineDispatcher,
    ): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(movieRepository, ioDispatcher)
    }
}
