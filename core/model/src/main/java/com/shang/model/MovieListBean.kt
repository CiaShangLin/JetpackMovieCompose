package com.shang.model

data class MovieListBean(
    val page: Int = 1,
    val results: List<Result> = listOf(),
    val totalPages: Int = 0,
    val totalResults: Int = 0,
) {
    data class Result(
        val adult: Boolean = false,
        val backdropPath: String = "",
        val genreIds: List<Int> = listOf(),
        val id: Int = 0,
        val originalLanguage: String = "",
        val originalTitle: String = "",
        val overview: String = "",
        val popularity: Double = 0.0,
        val posterPath: String = "",
        val releaseDate: String = "",
        val title: String = "",
        val video: Boolean = false,
        val voteAverage: Double = 0.0,
        val voteCount: Int = 0,
        //是否收藏
        var isCollect: Boolean = false,
    )
}
