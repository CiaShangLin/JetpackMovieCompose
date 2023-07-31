package com.shang.jetpackmoviecompose.ui.genre

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shang.jetpackmovie.bean.MovieListBean
import com.shang.jetpackmoviecompose.api.MovieApi
import com.shang.jetpackmoviecompose.repository.BaseRepository
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

class GenreRepository(private val mMovieApi: MovieApi) : BaseRepository() {

    fun getMovieGenreDetail(with_genres: String): Flow<PagingData<MovieListBean.Result>> {
        return Pager(
            config = PagingConfig(20),
            pagingSourceFactory = { GenrePagingSource(mMovieApi, with_genres) }
        ).flow
    }
}