package com.shang.network.model

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

/**
 * 首頁電影標籤電影
 */
@Serializable
data class DiscoverMovieResponse(
    @SerialName("page")
    val page: Int? = 0,
    @SerialName("results")
    val results: List<Result>? = listOf(),
    @SerialName("total_pages")
    val totalPages: Int? = 0,
    @SerialName("total_results")
    val totalResults: Int? = 0
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
        val voteCount: Int? = 0
    )
}
