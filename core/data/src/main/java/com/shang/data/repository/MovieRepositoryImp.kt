package com.shang.data.repository

import com.shang.common.UiState
import com.shang.database.dao.MovieDao
import com.shang.database.entity.MovieEntity
import com.shang.database.entity.asExtendedModel
import com.shang.model.MovieBean
import com.shang.model.MovieGenreBean
import com.shang.network.retrofit.MovieDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val movieDataSource: MovieDataSource,
    private val movieDao: MovieDao,
    private val ioDispatcher: CoroutineDispatcher,
) : MovieRepository {
    override fun getMovieGenres(): Flow<UiState<MovieGenreBean>> {
        return flow {
            emit(UiState.Loading)
            val response = movieDataSource.getMovieGenres()
            if (response.isSuccess && response.data != null) {
                emit(UiState.Success(response.data!!))
            } else {
                emit(UiState.Error(Exception(response.errorMessage)))
            }
        }.flowOn(ioDispatcher)
    }

    override fun getAllMovies(): Flow<List<MovieBean>> {
        return flow {
            emit(
                movieDao.getAllMovies().map {
                    it.asExtendedModel()
                },
            )
        }.flowOn(ioDispatcher)
    }
}
