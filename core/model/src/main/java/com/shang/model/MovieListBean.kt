package com.shang.model

/**
 * 代表電影列表的數據結構
 */
data class MovieListBean(
    val page: Int = 1,
    val results: List<MovieCardResult> = listOf(),
    val totalPages: Int = 0,
    val totalResults: Int = 0,
)
