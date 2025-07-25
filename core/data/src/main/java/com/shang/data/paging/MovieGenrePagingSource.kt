package com.shang.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shang.model.MovieCardResult
import com.shang.network.retrofit.MovieDataSource

class MovieGenrePagingSource(
    private val movieDataSource: MovieDataSource,
    private val withGenres: String,
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
                movieDataSource.getDiscoverMovie(withGenres = withGenres, page = params.key ?: 1)

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
                LoadResult.Error(response.error ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
