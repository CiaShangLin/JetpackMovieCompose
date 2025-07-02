package com.shang.domain.di

import com.shang.common.di.CommonDispatcher
import com.shang.common.di.Dispatcher
import com.shang.data.repository.MovieRepository
import com.shang.data.repository.UserDataRepository
import com.shang.domain.usecase.GetConfigurationUseCase
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
}
