package com.shang.data.di

import com.shang.common.di.CommonDispatcher
import com.shang.common.di.Dispatcher
import com.shang.data.paging.MovieGenrePagingSource
import com.shang.data.repository.MovieRepository
import com.shang.data.repository.MovieRepositoryImp
import com.shang.data.repository.UserDataRepository
import com.shang.data.repository.UserDataRepositoryImp
import com.shang.database.dao.MovieDao
import com.shang.datastore.UserPreferenceDataSource
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
        movieDao: MovieDao,
        @Dispatcher(CommonDispatcher.IO) dispatcher: CoroutineDispatcher,
    ): MovieRepository {
        return MovieRepositoryImp(movieDataSource, movieDao, dispatcher)
    }

    @Provides
    @Singleton
    fun provideUserDataRepository(
        userPreferenceDataSource: UserPreferenceDataSource,
    ): UserDataRepository {
        return UserDataRepositoryImp(userPreferenceDataSource)
    }

    @Provides
    @Singleton
    fun provideMovieGenrePagingSource(
        movieDataSource: MovieDataSource,
        withGenres: String,
    ): MovieGenrePagingSource {
        return MovieGenrePagingSource(movieDataSource, withGenres)
    }
}
