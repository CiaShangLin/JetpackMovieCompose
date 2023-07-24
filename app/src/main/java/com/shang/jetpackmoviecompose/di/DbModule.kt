package com.shang.jetpackmoviecompose.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.shang.jetpackmoviecompose.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {


    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, MovieDatabase.DB_NAME)
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieFavorDao(movieDatabase: MovieDatabase) = movieDatabase.movieFavorDao()

}