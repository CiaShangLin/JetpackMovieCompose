package com.shang.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shang.common.UiState
import com.shang.data.model.asEntity
import com.shang.data.paging.MovieGenrePagingSource
import com.shang.database.dao.MovieDao
import com.shang.database.entity.asExtendedModel
import com.shang.model.ConfigurationBean
import com.shang.model.MovieBean
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
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

    override fun getConfiguration(): Flow<Result<ConfigurationBean>> {
        return flow {
            val response = movieDataSource.getConfiguration()
            if (response.isSuccess) {
                emit(Result.success(response.data!!))
            } else {
                emit(Result.failure(response.error!!))
            }
        }.flowOn(ioDispatcher)
    }

    override fun getMovieGenres(): Flow<Result<MovieGenreBean>> {
        return flow {
            val response = movieDataSource.getMovieGenres()
            if (response.isSuccess) {
                emit(Result.success(response.data!!))
            } else {
                emit(Result.failure(Exception(response.error)))
            }
        }.flowOn(ioDispatcher)
    }

    override fun getDatabaseMovies(): Flow<List<MovieBean>> {
        return flow {
            emit(movieDao.getAllMovies().map { it.asExtendedModel() })
        }.flowOn(ioDispatcher)
    }

    override fun getDiscoverMovie(withGenres: String, page: Int): Flow<UiState<MovieListBean>> {
        return flow {
            emit(UiState.Loading)
            val response = movieDataSource.getDiscoverMovie(withGenres, page)
            if (response.isSuccess && response.data != null) {
                emit(UiState.Success(response.data!!))
            } else {
                emit(UiState.Error(Exception(response.error)))
            }
        }.flowOn(ioDispatcher)
    }

    override fun getMovieGenrePager(withGenres: String): Flow<PagingData<MovieListBean.Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20,
                prefetchDistance = 2,
            ),
            pagingSourceFactory = {
                MovieGenrePagingSource(movieDataSource, withGenres)
            },
        ).flow
            .flowOn(ioDispatcher)
    }

    override suspend fun insertMovie(movie: MovieBean) {
        movieDao.insertMovie(movie.asEntity())
    }
}
