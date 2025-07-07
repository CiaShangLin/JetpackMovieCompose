package com.shang.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.shang.model.MovieSearchBean
import com.shang.network.retrofit.MovieDataSource

class MovieSearchPagingSource(
    private val movieDataSource: MovieDataSource,
    private val query: String,
) : PagingSource<Int, MovieSearchBean.Result>() {
    override fun getRefreshKey(state: PagingState<Int, MovieSearchBean.Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieSearchBean.Result> {
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
