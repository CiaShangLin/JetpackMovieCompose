package com.shang.model

/**
 * 電影搜尋結果數據模型
 */
class MovieSearchBean(
    val page: Int = 1,
    val results: List<MovieCardResult> = listOf(),
    val totalPages: Int = 0,
    val totalResults: Int = 0,
)
