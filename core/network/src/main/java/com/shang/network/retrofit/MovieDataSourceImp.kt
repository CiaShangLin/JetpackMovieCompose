package com.shang.network.retrofit

import com.shang.model.ConfigurationBean
import com.shang.model.MovieCastAndCrewBean
import com.shang.model.MovieDetailBean
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
import com.shang.model.MovieRecommendBean
import com.shang.model.MovieSearchBean
import com.shang.network.extension.mapData
import com.shang.network.extension.safeApiCall
import com.shang.network.model.NetworkResponse
import com.shang.network.model.asExternalModel
import javax.inject.Inject

class MovieDataSourceImp @Inject constructor(private val movieApiService: MovieApiService) :
    MovieDataSource {

    override suspend fun getConfiguration(): NetworkResponse<ConfigurationBean> {
        return safeApiCall {
            movieApiService.getConfiguration()
        }.mapData { response ->
            response.asExternalModel()
        }
    }

    override suspend fun getMovieGenres(): NetworkResponse<MovieGenreBean> {
        return safeApiCall {
            movieApiService.getMovieGenres()
        }.mapData { response ->
            response.asExternalModel()
        }
    }

    override suspend fun getDiscoverMovie(withGenres: String, page: Int): NetworkResponse<MovieListBean> {
        return safeApiCall {
            movieApiService.getDiscoverMovie(withGenres, page)
        }.mapData { response ->
            response.asExternalModel()
        }
    }

    override suspend fun getMovieSearch(
        query: String,
        page: Int,
    ): NetworkResponse<MovieSearchBean> {
        return safeApiCall {
            movieApiService.getMovieSearch(query, page)
        }.mapData { response ->
            response.asExternalModel()
        }
    }

    override suspend fun getMovieDetail(id: Int): NetworkResponse<MovieDetailBean> {
        return safeApiCall {
            movieApiService.getMovieDetail(id)
        }.mapData { response ->
            response.asExternalModel()
        }
    }

    override suspend fun getMovieRecommendations(id: Int): NetworkResponse<MovieRecommendBean> {
        return safeApiCall {
            movieApiService.getMovieRecommendations(id)
        }.mapData { response ->
            response.asExternalModel()
        }
    }

    override suspend fun getMovieActor(id: Int): NetworkResponse<MovieCastAndCrewBean> {
        return safeApiCall {
            movieApiService.getMovieActor(id)
        }.mapData { response ->
            response.asExternalModel()
        }
    }
}
