package com.shang.jetpackmoviecompose.ui.genre

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shang.jetpackmovie.bean.MovieListBean
import com.shang.jetpackmoviecompose.api.MovieApi
import com.skydoves.sandwich.getOrNull

class GenrePagingSource(private val mMovieApi: MovieApi, private val mGenre: String) :
    PagingSource<Int, MovieListBean.Result>() {

    override fun getRefreshKey(state: PagingState<Int, MovieListBean.Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieListBean.Result> {
        return try {
            val page = params.key ?: 1
            val data = mMovieApi.getMovieGenreDetail(mGenre, page).getOrNull()
            LoadResult.Page(
                data = data?.results ?: listOf(),
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (data?.total_pages == page ) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}