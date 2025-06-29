package com.shang.network.retrofit

import com.shang.model.MovieGenreBean
import com.shang.network.extension.mapData
import com.shang.network.extension.safeApiCall
import com.shang.network.model.NetworkResponse
import com.shang.network.model.asExternalModel
import javax.inject.Inject

class MovieDataSourceImp @Inject constructor(private val _movieApiService: MovieApiService) :
    MovieDataSource {
    override suspend fun getMovieGenres(): NetworkResponse<MovieGenreBean> {
        return safeApiCall {
            _movieApiService.getMovieGenres()
        }.mapData { response ->
            response.asExternalModel()
        }
    }
}
