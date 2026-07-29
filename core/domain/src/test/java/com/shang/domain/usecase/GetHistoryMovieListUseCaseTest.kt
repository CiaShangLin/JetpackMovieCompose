package com.shang.domain.usecase

import com.shang.data.repository.MovieRepository
import com.shang.model.MovieCardResult
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
import strikt.assertions.containsExactly
import strikt.assertions.isEmpty

/**
 * GetHistoryMovieListUseCase 的單元測試
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("GetHistoryMovieListUseCase Tests")
class GetHistoryMovieListUseCaseTest {

    private val movieRepository = mockk<MovieRepository>()
    private lateinit var useCase: GetHistoryMovieListUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetHistoryMovieListUseCase(movieRepository, Dispatchers.Unconfined)
    }

    private fun movie(id: Int) = MovieCardResult(id = id, title = "movie-$id")

    @Test
    @DisplayName("歷史紀錄應依已收藏電影 id 清單標記 isCollect")
    fun `should mark isCollect according to collected movie ids`() = runTest {
        // Given
        val history = listOf(movie(1), movie(2), movie(3))
        every { movieRepository.getAllMovieHistory() } returns flowOf(history)
        every { movieRepository.getCollectedMovieIds() } returns flowOf(listOf(2))

        // When
        val result = useCase().first()

        // Then
        expectThat(result.map { it.id to it.isCollect })
            .containsExactly(1 to false, 2 to true, 3 to false)
    }

    @Test
    @DisplayName("無歷史紀錄時應回傳空清單")
    fun `should return empty list when there is no history`() = runTest {
        // Given
        every { movieRepository.getAllMovieHistory() } returns flowOf(emptyList())
        every { movieRepository.getCollectedMovieIds() } returns flowOf(emptyList())

        // When
        val result = useCase().first()

        // Then
        expectThat(result).isEmpty()
    }
}
