package com.shang.data.repository

import androidx.paging.PagingData
import com.shang.common.UiState
import com.shang.model.ConfigurationBean
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getConfiguration(): Flow<Result<ConfigurationBean>>

    fun getMovieGenres(): Flow<Result<MovieGenreBean>>

    fun getDiscoverMovie(withGenres: String, page: Int): Flow<UiState<MovieListBean>>

    fun getMovieGenrePager(withGenres: String): Flow<PagingData<MovieListBean.Result>>

    fun collectedMovieIds(): Flow<List<Int>>

    suspend fun insertMovie(movie: MovieListBean.Result)
}
