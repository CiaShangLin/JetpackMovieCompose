package com.shang.network.fixtures

import com.shang.model.ConfigurationBean
import com.shang.model.MovieCardResult
import com.shang.model.MovieDetailBean
import com.shang.model.MovieGenreBean
import com.shang.model.MovieListBean
import com.shang.network.model.ConfigurationResponse
import com.shang.network.model.DiscoverMovieResponse
import com.shang.network.model.MovieDetailResponse
import com.shang.network.model.MovieGenreResponse
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 測試資料工廠類別，提供各種測試場景所需的模擬資料。
 *
 * 遵循 Given-When-Then 測試模式，為不同測試案例提供一致且可重用的測試資料。
 * 包含成功回應、錯誤回應、異常情況等各種測試場景。
 */
object TestFixtures {

    // 成功回應的測試資料
    fun createSuccessfulConfigurationResponse(): Response<ConfigurationResponse> {
        val configResponse = ConfigurationResponse(
            images = ConfigurationResponse.Images(
                baseUrl = "https://image.tmdb.org/t/p/",
                secureBaseUrl = "https://image.tmdb.org/t/p/",
                backdropSizes = listOf("w300", "w780", "w1280", "original"),
                logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
                posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
                profileSizes = listOf("w45", "w185", "h632", "original"),
                stillSizes = listOf("w92", "w185", "w300", "original"),
            ),
            changeKeys = listOf("adult", "air_date", "also_known_as"),
        )
        return Response.success(200, configResponse)
    }

    fun createSuccessfulMovieGenreResponse(): Response<MovieGenreResponse> {
        val genreResponse = MovieGenreResponse(
            genres = listOf(
                MovieGenreResponse.Genre(id = 28, name = "Action"),
                MovieGenreResponse.Genre(id = 12, name = "Adventure"),
                MovieGenreResponse.Genre(id = 16, name = "Animation"),
            ),
        )
        return Response.success(200, genreResponse)
    }

    fun createSuccessfulDiscoverMovieResponse(): Response<DiscoverMovieResponse> {
        val discoverResponse = DiscoverMovieResponse(
            page = 1,
            results = listOf(
                DiscoverMovieResponse.Result(
                    id = 123,
                    title = "Test Movie",
                    overview = "Test movie overview",
                    posterPath = "/test_poster.jpg",
                    backdropPath = "/test_backdrop.jpg",
                    releaseDate = "2023-01-01",
                    voteAverage = 8.5,
                    voteCount = 1000,
                    genreIds = listOf(28, 12),
                    adult = false,
                    originalLanguage = "en",
                    originalTitle = "Test Movie Original",
                    popularity = 100.5,
                    video = false,
                ),
            ),
            totalPages = 10,
            totalResults = 200,
        )
        return Response.success(200, discoverResponse)
    }

    fun createSuccessfulMovieDetailResponse(): Response<MovieDetailResponse> {
        val detailResponse = MovieDetailResponse(
            id = 123,
            title = "Test Movie Detail",
            overview = "Detailed test movie overview",
            posterPath = "/test_detail_poster.jpg",
            backdropPath = "/test_detail_backdrop.jpg",
            releaseDate = "2023-01-01",
            voteAverage = 8.5,
            voteCount = 1000,
            runtime = 120,
            adult = false,
            originalLanguage = "en",
            originalTitle = "Test Movie Detail Original",
            popularity = 100.5,
            video = false,
            budget = 50000000,
            revenue = 200000000,
            status = "Released",
            tagline = "Test tagline",
            genres = listOf(
                MovieDetailResponse.Genre(id = 28, name = "Action"),
                MovieDetailResponse.Genre(id = 12, name = "Adventure"),
            ),
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            spokenLanguages = emptyList(),
        )
        return Response.success(200, detailResponse)
    }

    // 錯誤回應的測試資料
    fun createErrorResponse(): Response<Any> {
        return Response.error(404, "Not Found".toResponseBody(null))
    }

    fun createUnauthorizedResponse(): Response<Any> {
        return Response.error(401, "Unauthorized".toResponseBody(null))
    }

    fun createServerErrorResponse(): Response<Any> {
        return Response.error(500, "Internal Server Error".toResponseBody(null))
    }

    // 異常情況的測試資料
    fun createTimeoutException(): SocketTimeoutException {
        return SocketTimeoutException("Connection timeout")
    }

    fun createConnectionException(): ConnectException {
        return ConnectException("Connection failed")
    }

    fun createUnknownHostException(): UnknownHostException {
        return UnknownHostException("Unknown host")
    }

    // Domain 模型的測試資料
    fun createConfigurationBean(): ConfigurationBean {
        return ConfigurationBean(
            changeKeys = listOf("adult", "air_date", "also_known_as"),
            images = ConfigurationBean.Images(
                baseUrl = "https://image.tmdb.org/t/p/",
                secureBaseUrl = "https://image.tmdb.org/t/p/",
                backdropSizes = listOf("w300", "w780", "w1280", "original"),
                logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
                posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
                profileSizes = listOf("w45", "w185", "h632", "original"),
                stillSizes = listOf("w92", "w185", "w300", "original"),
            ),
        )
    }

    fun createMovieGenreBean(): MovieGenreBean {
        return MovieGenreBean(
            genres = listOf(
                MovieGenreBean.MovieGenre(id = 28, name = "Action"),
                MovieGenreBean.MovieGenre(id = 12, name = "Adventure"),
                MovieGenreBean.MovieGenre(id = 16, name = "Animation"),
            ),
        )
    }

    fun createMovieListBean(): MovieListBean {
        return MovieListBean(
            page = 1,
            results = listOf(
                MovieCardResult(
                    id = 123,
                    title = "Test Movie",
                    overview = "Test movie overview",
                    posterPath = "/test_poster.jpg",
                    backdropPath = "/test_backdrop.jpg",
                    releaseDate = "2023-01-01",
                    voteAverage = 8.5,
                    voteCount = 1000,
                    genreIds = listOf(28, 12),
                    adult = false,
                    originalLanguage = "en",
                    originalTitle = "Test Movie Original",
                    popularity = 100.5,
                    video = false,
                    isCollect = false,
                    timestamp = 0L,
                ),
            ),
            totalPages = 10,
            totalResults = 200,
        )
    }

    fun createMovieDetailBean(): MovieDetailBean {
        return MovieDetailBean(
            id = 123,
            title = "Test Movie Detail",
            overview = "Detailed test movie overview",
            posterPath = "/test_detail_poster.jpg",
            backdropPath = "/test_detail_backdrop.jpg",
            releaseDate = "2023-01-01",
            voteAverage = 8.5,
            voteCount = 1000,
            runtime = 120,
            adult = false,
            originalLanguage = "en",
            originalTitle = "Test Movie Detail Original",
            popularity = 100.5,
            video = false,
            budget = 50000000,
            revenue = 200000000,
            status = "Released",
            tagline = "Test tagline",
            genres = listOf(
                MovieDetailBean.Genre(id = 28, name = "Action"),
                MovieDetailBean.Genre(id = 12, name = "Adventure"),
            ),
        )
    }
}
