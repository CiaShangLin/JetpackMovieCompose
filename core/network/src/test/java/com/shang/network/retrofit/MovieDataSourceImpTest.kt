package com.shang.network.retrofit

import com.shang.network.model.ConfigurationResponse
import com.shang.network.model.asExternalModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
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

    @Nested
    @DisplayName("取得設定檔")
    inner class GetConfiguration {
        @Test
        @DisplayName("當 API 成功回傳，應回傳對應的 ConfigurationBean")
        fun `getConfiguration should return ConfigurationBean on success`() = runTest {
            // Given
            val networkResponse = mockk<ConfigurationResponse>()
            val expectedBean = networkResponse.asExternalModel()
            val response = Response.success(networkResponse)
            coEvery { movieApiService.getConfiguration() } returns response

            // When
            val result = movieDataSource.getConfiguration()

            // Then
            expectThat(result.isSuccess).isTrue()
            expectThat(result.data).isEqualTo(expectedBean)
        }
    }
//
//    @Nested
//    @DisplayName("取得電影類型")
//    inner class GetMovieGenres {
//        @Test
//        @DisplayName("當 API 成功回傳，應回傳對應的 MovieGenreBean")
//        fun `getMovieGenres should return MovieGenreBean on success`() = runTest {
//            // Given
//            val networkResponse = mockk<MovieGenreResponse>()
//            val expectedBean = networkResponse.asExternalModel()
//            coEvery { movieApiService.getMovieGenres() } returns networkResponse
//
//            // When
//            val result = movieDataSource.getMovieGenres()
//
//            // Then
//            expectThat(result.isSuccess).isTrue()
//            expectThat(result.data).isEqualTo(expectedBean)
//        }
//    }
//
//    @Nested
//    @DisplayName("發現電影")
//    inner class GetDiscoverMovie {
//        @Test
//        @DisplayName("當 API 成功回傳，應回傳對應的 MovieListBean")
//        fun `getDiscoverMovie should return MovieListBean on success`() = runTest {
//            // Given
//            val withGenres = "28"
//            val page = 1
//            val networkResponse = mockk<DiscoverMovieResponse>()
//            val expectedBean = networkResponse.asExternalModel()
//            coEvery { movieApiService.getDiscoverMovie(withGenres, page) } returns networkResponse
//
//            // When
//            val result = movieDataSource.getDiscoverMovie(withGenres, page)
//
//            // Then
//            expectThat(result.isSuccess).isTrue()
//            expectThat(result.data).isEqualTo(expectedBean)
//        }
//    }
//
//    @Nested
//    @DisplayName("搜尋電影")
//    inner class GetMovieSearch {
//        @Test
//        @DisplayName("當 API 成功回傳，應回傳對應的 MovieSearchBean")
//        fun `getMovieSearch should return MovieSearchBean on success`() = runTest {
//            // Given
//            val query = "Inception"
//            val page = 1
//            val networkResponse = mockk<SearchMovieResponse>()
//            val expectedBean = networkResponse.asExternalModel()
//            coEvery { movieApiService.getMovieSearch(query, page) } returns networkResponse
//
//            // When
//            val result = movieDataSource.getMovieSearch(query, page)
//
//            // Then
//            expectThat(result.isSuccess).isTrue()
//            expectThat(result.data).isEqualTo(expectedBean)
//        }
//    }
//
//    @Nested
//    @DisplayName("取得電影詳情")
//    inner class GetMovieDetail {
//        @Test
//        @DisplayName("當 API 成功回傳，應回傳對應的 MovieDetailBean")
//        fun `getMovieDetail should return MovieDetailBean on success`() = runTest {
//            // Given
//            val id = 123
//            val networkResponse = mockk<MovieDetailResponse>()
//            val expectedBean = networkResponse.asExternalModel()
//            coEvery { movieApiService.getMovieDetail(id) } returns networkResponse
//
//            // When
//            val result = movieDataSource.getMovieDetail(id)
//
//            // Then
//            expectThat(result.isSuccess).isTrue()
//            expectThat(result.data).isEqualTo(expectedBean)
//        }
//    }
//
//    @Nested
//    @DisplayName("取得推薦電影")
//    inner class GetMovieRecommendations {
//        @Test
//        @DisplayName("當 API 成功回傳，應回傳對應的 MovieRecommendBean")
//        fun `getMovieRecommendations should return MovieRecommendBean on success`() = runTest {
//            // Given
//            val id = 123
//            val networkResponse = mockk<MovieRecommendResponse>()
//            val expectedBean = networkResponse.asExternalModel()
//            coEvery { movieApiService.getMovieRecommendations(id) } returns networkResponse
//
//            // When
//            val result = movieDataSource.getMovieRecommendations(id)
//
//            // Then
//            expectThat(result.isSuccess).isTrue()
//            expectThat(result.data).isEqualTo(expectedBean)
//        }
//    }
//
//    @Nested
//    @DisplayName("取得電影演員")
//    inner class GetMovieActor {
//        @Test
//        @DisplayName("當 API 成功回傳，應回傳對應的 MovieCastAndCrewBean")
//        fun `getMovieActor should return MovieCastAndCrewBean on success`() = runTest {
//            // Given
//            val id = 123
//            val networkResponse = mockk<MovieCastAndCrewResponse>()
//            val expectedBean = networkResponse.asExternalModel()
//            coEvery { movieApiService.getMovieActor(id) } returns networkResponse
//
//            // When
//            val result = movieDataSource.getMovieActor(id)
//
//            // Then
//            expectThat(result.isSuccess).isTrue()
//            expectThat(result.data).isEqualTo(expectedBean)
//        }
//    }
}
