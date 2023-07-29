package com.shang.jetpackmoviecompose.ui.home

import com.shang.jetpackmovie.bean.MovieGenreBean
import com.shang.jetpackmoviecompose.api.BaseResponse
import com.shang.jetpackmoviecompose.api.MovieApi
import com.shang.jetpackmoviecompose.repository.BaseRepository
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val mMovieApi: MovieApi
) : BaseRepository() {
    suspend fun getMovieGenres(): ApiResponse<MovieGenreBean> {
        return  mMovieApi.getMovieGenres()
    }
}