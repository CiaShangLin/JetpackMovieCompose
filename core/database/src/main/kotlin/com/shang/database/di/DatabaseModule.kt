package com.shang.database.di

import android.content.Context
import androidx.room.Room
import com.shang.database.dao.MovieCollectDao
import com.shang.database.dao.MovieHistoryDao
import com.shang.database.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME,
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieCollectDao(database: AppDatabase): MovieCollectDao {
        return database.createMovieCollectDao()
    }

    @Provides
    @Singleton
    fun provideMovieHistoryDao(database: AppDatabase): MovieHistoryDao {
        return database.createMovieHistoryDao()
    }
}
