package com.shang.model

/**
 * 電影搜尋結果數據模型
 */
class MovieSearchBean(
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
        // 是否收藏
        var isCollect: Boolean = false,
        // 收藏時間戳
        val timestamp: Long = 0L,
    )
}

fun MovieSearchBean.Result.asMovieResult(): MovieListBean.Result {
    return MovieListBean.Result(
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
        id = this.id,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        isCollect = this.isCollect,
        timestamp = this.timestamp,
    )
}
