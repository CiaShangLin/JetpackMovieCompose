package com.shang.data.repository

import androidx.paging.PagingData
import com.shang.model.ConfigurationBean
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getConfiguration(): Flow<Result<ConfigurationBean>>

    fun getMovieGenres(): Flow<Result<MovieGenreBean>>

    fun getMovieListPager(withGenres: String): Flow<PagingData<MovieListBean.Result>>

    // MovieCollectDao
    suspend fun insertMovie(movieResult: MovieListBean.Result)

    suspend fun deleteMovie(movieResult: MovieListBean.Result)

    fun getCollectedMovieIds(): Flow<List<Int>>
}
