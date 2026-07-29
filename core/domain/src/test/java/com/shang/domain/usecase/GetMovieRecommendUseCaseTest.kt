package com.shang.domain.usecase

import com.shang.data.repository.MovieRepository
import com.shang.model.MovieCardResult
import com.shang.model.MovieRecommendBean
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isTrue

/**
 * GetMovieRecommendUseCase 的單元測試
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("GetMovieRecommendUseCase Tests")
class GetMovieRecommendUseCaseTest {

    private val movieRepository = mockk<MovieRepository>()
    private lateinit var useCase: GetMovieRecommendUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetMovieRecommendUseCase(movieRepository, Dispatchers.Unconfined)
    }

    private fun movie(id: Int) = MovieCardResult(id = id, title = "movie-$id")

    @Test
    @DisplayName("推薦電影應依已收藏電影 id 清單標記 isCollect")
    fun `should mark isCollect according to collected movie ids`() = runTest {
        // Given
        val recommendations = MovieRecommendBean(results = listOf(movie(1), movie(2)))
        every { movieRepository.getMovieRecommendations(1) } returns
            flowOf(Result.success(recommendations))
        every { movieRepository.getCollectedMovieIds() } returns flowOf(listOf(2))

        // When
        val result = useCase(1).first()

        // Then
        expectThat(result.getOrNull()?.map { it.id to it.isCollect })
            .isEqualTo(listOf(1 to false, 2 to true))
    }

    @Test
    @DisplayName("取得推薦電影失敗時應回傳原本的錯誤")
    fun `should emit original failure when fetching recommendations fails`() = runTest {
        // Given
        val error = RuntimeException("network error")
        every { movieRepository.getMovieRecommendations(1) } returns flowOf(Result.failure(error))
        every { movieRepository.getCollectedMovieIds() } returns flowOf(emptyList())

        // When
        val result = useCase(1).first()

        // Then
        expectThat(result.isFailure).isTrue()
        expectThat(result.exceptionOrNull()).isEqualTo(error)
    }
}
