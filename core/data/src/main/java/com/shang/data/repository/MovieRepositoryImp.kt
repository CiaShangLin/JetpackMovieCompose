package com.shang.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shang.data.model.asCollectEntity
import com.shang.data.model.asHistoryEntity
import com.shang.data.paging.MovieGenrePagingSource
import com.shang.data.paging.MovieSearchPagingSource
import com.shang.database.dao.MovieCollectDao
import com.shang.database.dao.MovieHistoryDao
import com.shang.database.entity.asExtendedModel
import com.shang.model.ConfigurationBean
import com.shang.model.MovieCardResult
import com.shang.model.MovieDetailBean
import com.shang.model.MovieGenreBean
import com.shang.model.MovieRecommendBean
import com.shang.network.retrofit.MovieDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 *
 */
class MovieRepositoryImp @Inject constructor(
    private val movieDataSource: MovieDataSource,
    private val movieCollectDao: MovieCollectDao,
    private val movieHistoryDao: MovieHistoryDao,
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

    override fun getMovieListPager(withGenres: String): Flow<PagingData<MovieCardResult>> {
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

    override fun getMovieSearchPager(query: String): Flow<PagingData<MovieCardResult>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20,
                prefetchDistance = 2,
            ),
            pagingSourceFactory = {
                MovieSearchPagingSource(movieDataSource, query)
            },
        ).flow
            .flowOn(ioDispatcher)
    }

    override fun getMovieDetail(id: Int): Flow<Result<MovieDetailBean>> {
        return flow {
            val response = movieDataSource.getMovieDetail(id)
            if (response.isSuccess) {
                emit(Result.success(response.data!!))
            } else {
                emit(Result.failure(response.error!!))
            }
        }.flowOn(ioDispatcher)
    }

    override fun getMovieRecommendations(id: Int): Flow<Result<MovieRecommendBean>> {
        return flow {
            val response = movieDataSource.getMovieRecommendations(id)
            if (response.isSuccess) {
                emit(Result.success(response.data!!))
            } else {
                emit(Result.failure(response.error!!))
            }
        }.flowOn(ioDispatcher)
    }

    override fun getCollectedMovieIds(): Flow<List<Int>> {
        return movieCollectDao.collectedMovieIds().flowOn(ioDispatcher)
    }

    override fun getAllMovieCollect(): Flow<List<MovieCardResult>> {
        return movieCollectDao.getAllMovies()
            .map {
                it.map { entity ->
                    entity.asExtendedModel()
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getMovieCollectEntityById(id: Int): Flow<MovieCardResult?> {
        return movieCollectDao.getMovieCollectEntityById(id)
            .map { entity ->
                entity?.asExtendedModel()
            }
            .flowOn(ioDispatcher)
    }

    override suspend fun insertMovieCollect(movieResult: MovieCardResult) {
        movieCollectDao.insertMovieCollect(movieResult.asCollectEntity())
    }

    override suspend fun deleteMovieCollect(movieResult: MovieCardResult) {
        movieCollectDao.deleteMovie(movieResult.asCollectEntity())
    }

    override suspend fun insertMovieHistory(movieResult: MovieCardResult) {
        movieHistoryDao.insertMovie(movieResult.asHistoryEntity())
    }

    override suspend fun deleteMovieHistory(movieResult: MovieCardResult) {
        movieHistoryDao.deleteMovie(movieResult.asHistoryEntity())
    }

    override fun getAllMovieHistory(): Flow<List<MovieCardResult>> {
        return movieHistoryDao.getAllMovies()
            .map { entities ->
                entities.map {
                    it.asExtendedModel()
                }
            }
            .flowOn(ioDispatcher)
    }

    override suspend fun deleteAllMovieHistory(): Boolean {
        return movieHistoryDao.deleteAllMovies() > 0
    }
}
