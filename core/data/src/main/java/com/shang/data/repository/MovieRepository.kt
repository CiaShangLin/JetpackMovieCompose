package com.shang.data.repository

import com.shang.common.UiState
import com.shang.model.MovieBean
import com.shang.model.MovieGenreBean
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovieGenres(): Flow<UiState<MovieGenreBean>>

    fun getDatabaseMovies(): Flow<List<MovieBean>>
}
