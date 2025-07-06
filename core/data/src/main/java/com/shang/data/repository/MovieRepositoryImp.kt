package com.shang.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.shang.data.model.asEntity
import com.shang.data.paging.MovieGenrePagingSource
import com.shang.database.dao.MovieCollectDao
import com.shang.model.ConfigurationBean
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
import com.shang.network.retrofit.MovieDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val movieDataSource: MovieDataSource,
    private val movieDao: MovieCollectDao,
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

    override fun getMovieListPager(withGenres: String): Flow<PagingData<MovieListBean.Result>> {
        val collectIdsFlow = movieDao.collectedMovieIds()
            .map { it.toSet() }
            .flowOn(ioDispatcher)
        return getMovieGenrePager(withGenres)
            .combine(collectIdsFlow) { pagingData, collectIds ->
                pagingData.map { movie ->
                    movie.copy(isCollect = collectIds.contains(movie.id))
                }
            }
            .flowOn(ioDispatcher)
    }

    override suspend fun insertMovie(movie: MovieListBean.Result) {
        movieDao.insertMovie(movie.asEntity())
    }

    override suspend fun deleteMovie(movie: MovieListBean.Result) {
        movieDao.deleteMovie(movie.asEntity())
    }
}
