package com.shang.jetpackmoviecompose.di

import com.shang.jetpackmoviecompose.room.dao.MovieFavorDao
import com.shang.jetpackmoviecompose.ui.favorites.FavoritesRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoritesModule {

    @Singleton
    @Provides
    fun provideFavoritesRepository(movieFavorDao: MovieFavorDao) =
        FavoritesRepository(movieFavorDao)
}