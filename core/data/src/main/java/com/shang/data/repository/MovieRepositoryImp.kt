package com.shang.data.repository

import com.shang.common.UiState
import com.shang.model.MovieGenreBean
import com.shang.network.retrofit.MovieDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val _movieDataSource: MovieDataSource,
    private val _ioDispatcher: CoroutineDispatcher,
) : MovieRepository {
    override fun getMovieGenres(): Flow<UiState<MovieGenreBean>> {
        return flow {
            emit(UiState.Loading)
            val response = _movieDataSource.getMovieGenres()
            if (response.isSuccess && response.data != null) {
                emit(UiState.Success(response.data!!))
            } else {
                emit(UiState.Error(Exception(response.errorMessage)))
            }
        }.flowOn(_ioDispatcher)
    }
}
