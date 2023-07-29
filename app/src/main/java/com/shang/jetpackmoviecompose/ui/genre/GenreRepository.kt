package com.shang.jetpackmoviecompose.ui.genre

import com.shang.jetpackmovie.bean.MovieListBean
import com.shang.jetpackmoviecompose.api.MovieApi
import com.shang.jetpackmoviecompose.repository.BaseRepository
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Query

class GenreRepository(private val mMovieApi: MovieApi) :BaseRepository() {

   suspend fun getMovieGenreDetail(with_genres: String, page: Int): ApiResponse<MovieListBean> {
        return mMovieApi.getMovieGenreDetail(with_genres, page)
    }
}