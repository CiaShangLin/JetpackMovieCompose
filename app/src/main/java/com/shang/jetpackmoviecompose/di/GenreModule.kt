package com.shang.jetpackmoviecompose.di

import com.shang.jetpackmoviecompose.api.MovieApi
import com.shang.jetpackmoviecompose.ui.genre.GenreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GenreModule {


    @Singleton
    @Provides
    fun provideGenreRepository(movieApi: MovieApi) = GenreRepository(movieApi)

}