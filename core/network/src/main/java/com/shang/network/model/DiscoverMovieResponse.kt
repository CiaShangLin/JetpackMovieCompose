package com.shang.network.model

import com.shang.model.MovieCardResult
import com.shang.model.MovieListBean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 首頁電影標籤電影
 */
@Serializable
data class DiscoverMovieResponse(
    @SerialName("page")
    val page: Int? = 1,
    @SerialName("results")
    val results: List<Result>? = listOf(),
    @SerialName("total_pages")
    val totalPages: Int? = 0,
    @SerialName("total_results")
    val totalResults: Int? = 0,
) {
    @Serializable
    data class Result(
        @SerialName("adult")
        val adult: Boolean? = false,
        @SerialName("backdrop_path")
        val backdropPath: String? = "",
        @SerialName("genre_ids")
        val genreIds: List<Int>? = listOf(),
        @SerialName("id")
        val id: Int? = 0,
        @SerialName("original_language")
        val originalLanguage: String? = "",
        @SerialName("original_title")
        val originalTitle: String? = "",
        @SerialName("overview")
        val overview: String? = "",
        @SerialName("popularity")
        val popularity: Double? = 0.0,
        @SerialName("poster_path")
        val posterPath: String? = "",
        @SerialName("release_date")
        val releaseDate: String? = "",
        @SerialName("title")
        val title: String? = "",
        @SerialName("video")
        val video: Boolean? = false,
        @SerialName("vote_average")
        val voteAverage: Double? = 0.0,
        @SerialName("vote_count")
        val voteCount: Int? = 0,
    )
}

fun DiscoverMovieResponse.asExternalModel(): MovieListBean {
    return MovieListBean(
        page = page ?: 1,
        results = results?.map { it.asExternalModel() } ?: listOf(),
        totalPages = totalPages ?: 0,
        totalResults = totalResults ?: 0,
    )
}

fun DiscoverMovieResponse.Result.asExternalModel(): MovieCardResult {
    return MovieCardResult(
        adult = adult ?: false,
        backdropPath = backdropPath ?: "",
        genreIds = genreIds ?: listOf(),
        id = id ?: 0,
        originalLanguage = originalLanguage ?: "",
        originalTitle = originalTitle ?: "",
        overview = overview ?: "",
        popularity = popularity ?: 0.0,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate ?: "",
        title = title ?: "",
        video = video ?: false,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
    )
}
