package com.shang.data.repository

import androidx.paging.PagingData
import com.shang.common.UiState
import com.shang.model.ConfigurationBean
import com.shang.model.MovieBean
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getConfiguration(): Flow<Result<ConfigurationBean>>

    fun getMovieGenres(): Flow<Result<MovieGenreBean>>

    fun getDatabaseMovies(): Flow<List<MovieBean>>

    fun getDiscoverMovie(withGenres: String, page: Int): Flow<UiState<MovieListBean>>

    fun getMovieGenrePager(withGenres: String): Flow<PagingData<MovieListBean.Result>>

    suspend fun insertMovie(movie: MovieBean)
}
