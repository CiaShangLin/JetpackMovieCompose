package com.shang.network.retrofit

import com.shang.network.model.ConfigurationResponse
import com.shang.network.model.DiscoverMovieResponse
import com.shang.network.model.MovieCastAndCrewResponse
import com.shang.network.model.MovieDetailResponse
import com.shang.network.model.MovieGenreResponse
import com.shang.network.model.MovieRecommendResponse
import com.shang.network.model.SearchMovieResponse
import com.shang.network.model.asExternalModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isTrue

@DisplayName("MovieDataSourceImp 單元測試")
@ExtendWith(MockKExtension::class)
class MovieDataSourceImpTest {

    @MockK
    private lateinit var movieApiService: MovieApiService

    private lateinit var movieDataSource: MovieDataSourceImp

    @BeforeEach
    fun setUp() {
        movieDataSource = MovieDataSourceImp(movieApiService)
    }

    @Test
    @DisplayName("當 API 成功回傳，應回傳對應的 ConfigurationBean")
    fun `getConfiguration should return ConfigurationBean on success`() = runTest {
        // Arrange
        val networkResponse = ConfigurationResponse(
            changeKeys = listOf("key1", "key2"),
            images = ConfigurationResponse.Images(
                backdropSizes = listOf("w300", "w780"),
                baseUrl = "http://image.tmdb.org/t/p/",
                logoSizes = listOf("w45", "w92"),
                posterSizes = listOf("w154", "w342"),
                profileSizes = listOf("w45", "w185"),
                secureBaseUrl = "https://image.tmdb.org/t/p/",
                stillSizes = listOf("w92", "w185"),
            ),
        )
        val expectedBean = networkResponse.asExternalModel()
        val response = Response.success(networkResponse)
        coEvery { movieApiService.getConfiguration() } returns response

        // Act
        val result = movieDataSource.getConfiguration()

        // Assert
        expectThat(result.isSuccess).isTrue()
        expectThat(result.data).isEqualTo(expectedBean)
    }

    @Test
    @DisplayName("當 API 成功回傳，應回傳對應的 MovieGenreBean")
    fun `getMovieGenres should return MovieGenreBean on success`() = runTest {
        // Given
        val networkResponse = MovieGenreResponse()
        val expectedBean = networkResponse.asExternalModel()
        val response = Response.success(networkResponse)
        coEvery { movieApiService.getMovieGenres() } returns response

        // When
        val result = movieDataSource.getMovieGenres()

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(result.data).isEqualTo(expectedBean)
    }

    @Test
    @DisplayName("當 API 成功回傳，應回傳對應的 MovieListBean")
    fun `getDiscoverMovie should return MovieListBean on success`() = runTest {
        // Given
        val withGenres = "28"
        val page = 1
        val networkResponse = DiscoverMovieResponse()
        val expectedBean = networkResponse.asExternalModel()
        val response = Response.success(networkResponse)
        coEvery { movieApiService.getDiscoverMovie(withGenres, page) } returns response

        // When
        val result = movieDataSource.getDiscoverMovie(withGenres, page)

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(result.data).isEqualTo(expectedBean)
    }

    @Test
    @DisplayName("當 API 成功回傳，應回傳對應的 MovieSearchBean")
    fun `getMovieSearch should return MovieSearchBean on success`() = runTest {
        // Arrange
        val query = "Inception"
        val page = 1
        val networkResponse = SearchMovieResponse()
        val expectedBean = networkResponse.asExternalModel()
        val response = Response.success(networkResponse)
        coEvery { movieApiService.getMovieSearch(query, page) } returns response

        // Act
        val result = movieDataSource.getMovieSearch(query, page)

        // Assert
        expectThat(result.isSuccess).isTrue()
        expectThat(result.data!!.page).isEqualTo(expectedBean.page)
    }

    @Test
    @DisplayName("當 API 成功回傳，應回傳對應的 MovieDetailBean")
    fun `getMovieDetail should return MovieDetailBean on success`() = runTest {
        // Given
        val id = 123
        val networkResponse = MovieDetailResponse()
        val expectedBean = networkResponse.asExternalModel()
        val response = Response.success(networkResponse)
        coEvery { movieApiService.getMovieDetail(id) } returns response

        // When
        val result = movieDataSource.getMovieDetail(id)

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(result.data).isEqualTo(expectedBean)
    }

    @Test
    @DisplayName("當 API 成功回傳，應回傳對應的 MovieRecommendBean")
    fun `getMovieRecommendations should return MovieRecommendBean on success`() = runTest {
        // Given
        val id = 123
        val networkResponse = MovieRecommendResponse()
        val expectedBean = networkResponse.asExternalModel()
        val response = Response.success(networkResponse)
        coEvery { movieApiService.getMovieRecommendations(id) } returns response

        // When
        val result = movieDataSource.getMovieRecommendations(id)

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(result.data!!.page).isEqualTo(expectedBean.page)
    }

    @Test
    @DisplayName("當 API 成功回傳，應回傳對應的 MovieCastAndCrewBean")
    fun `getMovieActor should return MovieCastAndCrewBean on success`() = runTest {
        // Given
        val id = 123
        val networkResponse = MovieCastAndCrewResponse()
        val expectedBean = networkResponse.asExternalModel()
        val response = Response.success(networkResponse)
        coEvery { movieApiService.getMovieActor(id) } returns response

        // When
        val result = movieDataSource.getMovieActor(id)

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(result.data).isEqualTo(expectedBean)
    }
}
