package com.shang.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.shang.model.MovieListBean
import com.shang.network.retrofit.MovieDataSource

class MovieSearchPagingSource(
    private val movieDataSource: MovieDataSource,
    private val query: String,
) : PagingSource<Int, MovieListBean.Result>() {
    override fun getRefreshKey(state: PagingState<Int, MovieListBean.Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieListBean.Result> {
        return try {
            val page = params.key ?: 1
            val response =
                movieDataSource.getMovieSearch(query = query, page = params.key ?: 1)

            val prevKey = if (page == 1) {
                null
            } else {
                page - 1
            }
            val nextKey = if (page == response.data?.totalPages) {
                null
            } else {
                page + 1
            }
            LoadResult.Page(
                data = response.data?.results ?: emptyList(),
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
