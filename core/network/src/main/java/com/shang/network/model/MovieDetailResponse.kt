package com.shang.network.model

import android.annotation.SuppressLint
import com.shang.model.MovieDetailBean
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 電影詳情數據模型
 */
@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class MovieDetailResponse(
    @SerialName("adult")
    val adult: Boolean? = false,
    @SerialName("backdrop_path")
    val backdropPath: String? = "",
    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = BelongsToCollection(),
    @SerialName("budget")
    val budget: Int? = 0,
    @SerialName("genres")
    val genres: List<Genre>? = listOf(),
    @SerialName("homepage")
    val homepage: String? = "",
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("imdb_id")
    val imdbId: String? = "",
    @SerialName("origin_country")
    val originCountry: List<String>? = listOf(),
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
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany>? = listOf(),
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>? = listOf(),
    @SerialName("release_date")
    val releaseDate: String? = "",
    @SerialName("revenue")
    val revenue: Int? = 0,
    @SerialName("runtime")
    val runtime: Int? = 0,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>? = listOf(),
    @SerialName("status")
    val status: String? = "",
    @SerialName("tagline")
    val tagline: String? = "",
    @SerialName("title")
    val title: String? = "",
    @SerialName("video")
    val video: Boolean? = false,
    @SerialName("vote_average")
    val voteAverage: Double? = 0.0,
    @SerialName("vote_count")
    val voteCount: Int? = 0,
) {

    @Serializable
    data class BelongsToCollection(
        @SerialName("backdrop_path")
        val backdropPath: String? = "",
        @SerialName("id")
        val id: Int? = 0,
        @SerialName("name")
        val name: String? = "",
        @SerialName("poster_path")
        val posterPath: String? = "",
    )

    @Serializable
    data class Genre(
        @SerialName("id")
        val id: Int? = 0,
        @SerialName("name")
        val name: String? = "",
    )

    @Serializable
    data class ProductionCompany(
        @SerialName("id")
        val id: Int? = 0,
        @SerialName("logo_path")
        val logoPath: String? = "",
        @SerialName("name")
        val name: String? = "",
        @SerialName("origin_country")
        val originCountry: String? = "",
    )

    @Serializable
    data class ProductionCountry(
        @SerialName("iso_3166_1")
        val iso31661: String? = "",
        @SerialName("name")
        val name: String? = "",
    )

    @Serializable
    data class SpokenLanguage(
        @SerialName("english_name")
        val englishName: String? = "",
        @SerialName("iso_639_1")
        val iso6391: String? = "",
        @SerialName("name")
        val name: String? = "",
    )
}

fun MovieDetailResponse.asExternalModel(): MovieDetailBean {
    return MovieDetailBean(
        adult = adult ?: false,
        backdropPath = backdropPath ?: "",
        belongsToCollection = belongsToCollection?.let {
            MovieDetailBean.BelongsToCollection(
                backdropPath = it.backdropPath ?: "",
                id = it.id ?: 0,
                name = it.name ?: "",
                posterPath = it.posterPath ?: "",
            )
        },
        budget = budget ?: 0,
        genres = genres?.map { MovieDetailBean.Genre(it.id ?: 0, it.name ?: "") } ?: emptyList(),
        homepage = homepage ?: "",
        id = id ?: 0,
        imdbId = imdbId ?: "",
        originCountry = originCountry ?: emptyList(),
        originalLanguage = originalLanguage ?: "",
        originalTitle = originalTitle ?: "",
        overview = overview ?: "",
        popularity = popularity ?: 0.0,
        posterPath = posterPath ?: "",
        productionCompanies = productionCompanies?.map {
            MovieDetailBean.ProductionCompany(
                id = it.id ?: 0,
                logoPath = it.logoPath ?: "",
                name = it.name ?: "",
                originCountry = it.originCountry ?: "",
            )
        } ?: emptyList(),
        productionCountries = productionCountries?.map {
            MovieDetailBean.ProductionCountry(
                iso31661 = it.iso31661 ?: "",
                name = it.name ?: "",
            )
        } ?: emptyList(),
        releaseDate = releaseDate ?: "",
        revenue = revenue ?: 0,
        runtime = runtime ?: 0,
        spokenLanguages = spokenLanguages?.map {
            MovieDetailBean.SpokenLanguage(
                englishName = it.englishName ?: "",
                iso6391 = it.iso6391 ?: "",
                name = it.name ?: "",
            )
        } ?: emptyList(),
        status = status ?: "",
        tagline = tagline ?: "",
        title = title ?: "",
        video = video ?: false,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
    )
}
