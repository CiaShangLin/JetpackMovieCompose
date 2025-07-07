package com.shang.network.retrofit

import com.shang.model.ConfigurationBean
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
import com.shang.model.MovieSearchBean
import com.shang.network.extension.mapData
import com.shang.network.extension.safeApiCall
import com.shang.network.model.NetworkResponse
import com.shang.network.model.asExternalModel
import javax.inject.Inject

class MovieDataSourceImp @Inject constructor(private val _movieApiService: MovieApiService) :
    MovieDataSource {

    override suspend fun getConfiguration(): NetworkResponse<ConfigurationBean> {
        return safeApiCall {
            _movieApiService.getConfiguration()
        }.mapData { response ->
            response.asExternalModel()
        }
    }

    override suspend fun getMovieGenres(): NetworkResponse<MovieGenreBean> {
        return safeApiCall {
            _movieApiService.getMovieGenres()
        }.mapData { response ->
            response.asExternalModel()
        }
    }

    override suspend fun getDiscoverMovie(withGenres: String, page: Int): NetworkResponse<MovieListBean> {
        return safeApiCall {
            _movieApiService.getDiscoverMovie(withGenres, page)
        }.mapData { response ->
            response.asExternalModel()
        }
    }

    override suspend fun getMovieSearch(
        query: String,
        page: Int,
    ): NetworkResponse<MovieSearchBean> {
        return safeApiCall {
            _movieApiService.getMovieSearch(query, page)
        }.mapData { response ->
            response.asExternalModel()
        }
    }
}
