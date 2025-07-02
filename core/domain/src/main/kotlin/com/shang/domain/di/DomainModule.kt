package com.shang.domain.di

import com.shang.data.repository.MovieRepository
import com.shang.data.repository.UserDataRepository
import com.shang.domain.usecase.GetConfigurationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetConfigurationUseCase(
        movieRepository: MovieRepository,
        userDataRepository: UserDataRepository,
    ): GetConfigurationUseCase {
        return GetConfigurationUseCase(movieRepository, userDataRepository)
    }
}
