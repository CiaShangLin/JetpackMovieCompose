package com.shang.network.model
import android.annotation.SuppressLint
import com.shang.model.MovieCardResult
import com.shang.model.MovieRecommendBean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 電影推薦數據模型
 */
@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MovieRecommendResponse(
    @SerialName("page")
    val page: Int? = 0,
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
        @SerialName("media_type")
        val mediaType: String? = "",
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

fun MovieRecommendResponse.asExternalModel(): MovieRecommendBean {
    return MovieRecommendBean(
        page = this.page ?: 1,
        results = this.results?.map {
            it.asExternalModel()
        } ?: emptyList(),
        totalPages = this.totalPages ?: 0,
        totalResults = this.totalResults ?: 0,
    )
}

fun MovieRecommendResponse.Result.asExternalModel(): MovieCardResult {
    return MovieCardResult(
        adult = this.adult ?: false,
        backdropPath = this.backdropPath ?: "",
        genreIds = this.genreIds ?: listOf(),
        id = this.id ?: 0,
        originalLanguage = this.originalLanguage ?: "",
        originalTitle = this.originalTitle ?: "",
        overview = this.overview ?: "",
        popularity = this.popularity ?: 0.0,
        posterPath = this.posterPath ?: "",
        releaseDate = this.releaseDate ?: "",
        title = this.title ?: "",
        video = this.video ?: false,
        voteAverage = this.voteAverage ?: 0.0,
        voteCount = this.voteCount ?: 0,
    )
}
