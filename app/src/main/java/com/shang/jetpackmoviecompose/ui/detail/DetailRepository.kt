package com.shang.jetpackmoviecompose.ui.detail

import com.shang.jetpackmovie.bean.ActorBean
import com.shang.jetpackmovie.bean.MovieDetailBean
import com.shang.jetpackmovie.bean.MovieListBean
import com.shang.jetpackmoviecompose.api.MovieApi
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Path
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val mMovieApi: MovieApi
) {
    suspend fun getMovieDetail(id: Int): ApiResponse<MovieDetailBean> {
        return mMovieApi.getMovieDetail(id)
    }

    suspend fun getMovieActor(id: Int): ApiResponse<ActorBean> {
        return mMovieApi.getMovieActor(id)
    }

    suspend fun getMovieRecommendations(id: Int): ApiResponse<MovieListBean> {
        return mMovieApi.getMovieRecommendations(id)
    }
}