package com.shang.data.repository

import com.shang.model.MovieGenreBean
import com.shang.network.retrofit.MovieDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(private val _movieDataSource: MovieDataSource) : MovieRepository {
    override fun getMovieGenres(): Flow<MovieGenreBean> {
        return flow {
            val response = _movieDataSource.getMovieGenres()
            if (response.isSuccess) {
                emit(response.data!!)
            }
        }
    }
}
