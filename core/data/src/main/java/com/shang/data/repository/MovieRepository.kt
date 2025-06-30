package com.shang.data.repository

import com.shang.common.UiState
import com.shang.model.MovieGenreBean
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovieGenres(dispatcher: CoroutineDispatcher): Flow<UiState<MovieGenreBean>>
}
