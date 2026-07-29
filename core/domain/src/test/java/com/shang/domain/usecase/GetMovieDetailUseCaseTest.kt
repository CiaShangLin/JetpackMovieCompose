package com.shang.domain.usecase

import com.shang.data.repository.MovieRepository
import com.shang.model.MovieDetailBean
import com.shang.model.asMovieCardResult
import io.mockk.coVerify
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
 * GetMovieDetailUseCase 的單元測試
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("GetMovieDetailUseCase Tests")
class GetMovieDetailUseCaseTest {

    private val movieRepository = mockk<MovieRepository>(relaxUnitFun = true)
    private lateinit var useCase: GetMovieDetailUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetMovieDetailUseCase(movieRepository, Dispatchers.Unconfined)
    }

    @Test
    @DisplayName("取得電影詳情成功時應寫入歷史紀錄")
    fun `should insert movie history when detail fetched successfully`() = runTest {
        // Given
        val detail = MovieDetailBean(id = 1, title = "movie-1")
        every { movieRepository.getMovieDetail(1) } returns flowOf(Result.success(detail))

        // When
        val result = useCase(1).first()

        // Then
        expectThat(result.getOrNull()).isEqualTo(detail)
        coVerify { movieRepository.insertMovieHistory(detail.asMovieCardResult()) }
    }

    @Test
    @DisplayName("取得電影詳情失敗時不應寫入歷史紀錄")
    fun `should not insert movie history when detail fetch fails`() = runTest {
        // Given
        val error = RuntimeException("network error")
        every { movieRepository.getMovieDetail(1) } returns flowOf(Result.failure(error))

        // When
        val result = useCase(1).first()

        // Then
        expectThat(result.isFailure).isTrue()
        coVerify(exactly = 0) { movieRepository.insertMovieHistory(any()) }
    }
}
