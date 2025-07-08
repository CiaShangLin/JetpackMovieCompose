package com.shang.model

/**
 * 電影詳情數據模型
 */
data class MovieDetailBean(
    val adult: Boolean = false,
    val backdropPath: String = "",
    val belongsToCollection: BelongsToCollection? = null,
    val budget: Int = 0,
    val genres: List<Genre> = emptyList(),
    val homepage: String = "",
    val id: Int = 0,
    val imdbId: String = "",
    val originCountry: List<String> = emptyList(),
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String = "",
    val productionCompanies: List<ProductionCompany> = emptyList(),
    val productionCountries: List<ProductionCountry> = emptyList(),
    val releaseDate: String = "",
    val revenue: Int = 0,
    val runtime: Int = 0,
    val spokenLanguages: List<SpokenLanguage> = emptyList(),
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
) {
    /**
     * 電影系列集合資訊
     */
    data class BelongsToCollection(
        val backdropPath: String = "",
        val id: Int = 0,
        val name: String = "",
        val posterPath: String = "",
    )

    /**
     * 電影類型資訊
     */
    data class Genre(
        val id: Int = 0,
        val name: String = "",
    )

    /**
     * 製作公司資訊
     */
    data class ProductionCompany(
        val id: Int = 0,
        val logoPath: String = "",
        val name: String = "",
        val originCountry: String = "",
    )

    /**
     * 製作國家資訊
     */
    data class ProductionCountry(
        val iso31661: String = "",
        val name: String = "",
    )

    /**
     * 語言資訊
     */
    data class SpokenLanguage(
        val englishName: String = "",
        val iso6391: String = "",
        val name: String = "",
    )
}

fun MovieDetailBean.asMovieCardResult(): MovieCardResult {
    return MovieCardResult(
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        voteCount = voteCount,
        genreIds = genres.map { it.id },
    )
}
