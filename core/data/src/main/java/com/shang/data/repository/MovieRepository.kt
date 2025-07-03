package com.shang.data.repository

import com.shang.common.UiState
import com.shang.model.ConfigurationBean
import com.shang.model.MovieBean
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getConfiguration(): Flow<UiState<ConfigurationBean>>

    fun getMovieGenres(): Flow<UiState<MovieGenreBean>>

    fun getDatabaseMovies(): Flow<List<MovieBean>>

    fun getDiscoverMovie(withGenres: String, page: Int): Flow<UiState<MovieListBean>>

    suspend fun insertMovie(movie: MovieBean)
}
