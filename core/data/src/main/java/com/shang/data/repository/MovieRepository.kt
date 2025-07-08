package com.shang.data.repository

import androidx.paging.PagingData
import com.shang.model.ConfigurationBean
import com.shang.model.MovieCardResult
import com.shang.model.MovieDetailBean
import com.shang.model.MovieGenreBean
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getConfiguration(): Flow<Result<ConfigurationBean>>

    fun getMovieGenres(): Flow<Result<MovieGenreBean>>

    fun getMovieListPager(withGenres: String): Flow<PagingData<MovieCardResult>>

    fun getMovieSearchPager(query: String): Flow<PagingData<MovieCardResult>>

    fun getMovieDetail(id: Int): Flow<Result<MovieDetailBean>>

    // MovieCollectDao
    suspend fun insertMovieCollect(movieResult: MovieCardResult)

    suspend fun deleteMovieCollect(movieResult: MovieCardResult)

    fun getCollectedMovieIds(): Flow<List<Int>>

    fun getAllMovieCollect(): Flow<List<MovieCardResult>>

    fun getMovieCollectEntityById(id: Int): Flow<MovieCardResult?>

    // MovieHistoryDao
    suspend fun insertMovieHistory(movieResult: MovieCardResult)

    suspend fun deleteMovieHistory(movieResult: MovieCardResult)

    fun getAllMovieHistory(): Flow<List<MovieCardResult>>
}
