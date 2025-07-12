package com.shang.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shang.model.MovieCardResult
import com.shang.network.retrofit.MovieDataSource

class MovieSearchPagingSource(
    private val movieDataSource: MovieDataSource,
    private val query: String,
) : PagingSource<Int, MovieCardResult>() {
    override fun getRefreshKey(state: PagingState<Int, MovieCardResult>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieCardResult> {
        return try {
            val page = params.key ?: 1
            val response =
                movieDataSource.getMovieSearch(query = query, page = params.key ?: 1)

            if (response.isSuccess) {
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
            } else {
                LoadResult.Error(
                    response.error?.cause ?: Exception("Unknown error occurred"),
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
