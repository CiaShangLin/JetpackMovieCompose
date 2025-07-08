package com.shang.data.repository

import androidx.paging.PagingData
import com.shang.model.ConfigurationBean
import com.shang.model.MovieCardResult
import com.shang.model.MovieGenreBean
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getConfiguration(): Flow<Result<ConfigurationBean>>

    fun getMovieGenres(): Flow<Result<MovieGenreBean>>

    fun getMovieListPager(withGenres: String): Flow<PagingData<MovieCardResult>>

    fun getMovieSearchPager(query: String): Flow<PagingData<MovieCardResult>>

    // MovieCollectDao
    suspend fun insertMovie(movieResult: MovieCardResult)

    suspend fun deleteMovie(movieResult: MovieCardResult)

    fun getCollectedMovieIds(): Flow<List<Int>>

    fun getAllMovieCollect(): Flow<List<MovieCardResult>>
}
