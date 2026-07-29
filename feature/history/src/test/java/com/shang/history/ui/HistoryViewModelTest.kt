package com.shang.history.ui

import com.shang.data.repository.MovieRepository
import com.shang.domain.usecase.GetHistoryMovieListUseCase
import com.shang.model.MovieCardResult
import com.shang.ui.MovieCardData
import com.shang.ui.asMovieCardResult
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly

/**
 * HistoryViewModel 的單元測試
 *
 * 驗證 allMovieHistory 依 UseCase 回應更新、toggleMovieCollectStatus 加入移除收藏、clearAllMovieHistory 清空歷史紀錄
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("HistoryViewModel Tests")
class HistoryViewModelTest {

    private val movieRepository = mockk<MovieRepository>()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        clearAllMocks()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(history: List<MovieCardResult>): HistoryViewModel {
        every { movieRepository.getAllMovieHistory() } returns flowOf(history)
        every { movieRepository.getCollectedMovieIds() } returns flowOf(emptyList())
        val getHistoryMovieListUseCase = GetHistoryMovieListUseCase(movieRepository, Dispatchers.Unconfined)
        return HistoryViewModel(movieRepository, getHistoryMovieListUseCase)
    }

    private fun movieCardData(isCollect: Boolean) = MovieCardData(
        movieCardId = 1,
        movieCardTitle = "title",
        movieCardPosterPath = "poster",
        movieCardReleaseDate = "2024-01-01",
        movieCardVoteAverage = 8.0,
        movieCardIsCollect = isCollect,
        movieCardTimestamp = 0L,
    )

    @Test
    @DisplayName("有歷史紀錄時 allMovieHistory 應反映 UseCase 回傳結果")
    fun `allMovieHistory should reflect history from use case`() = runTest {
        // Given
        val history = listOf(MovieCardResult(id = 1, title = "movie-1"))
        val viewModel = createViewModel(history)

        // When
        val job = launch { viewModel.allMovieHistory.collect {} }
        advanceUntilIdle()

        // Then
        expectThat(viewModel.allMovieHistory.value.map { it.id }).containsExactly(1)
        job.cancel()
    }

    @Test
    @DisplayName("尚未收藏的電影切換收藏狀態時，應呼叫 insertMovieCollect")
    fun `should insert movie collect when movie is not collected yet`() = runTest {
        // Given
        coEvery { movieRepository.insertMovieCollect(any()) } returns Unit
        val viewModel = createViewModel(emptyList())
        val data = movieCardData(isCollect = false)

        // When
        viewModel.toggleMovieCollectStatus(data)
        advanceUntilIdle()

        // Then
        coVerify { movieRepository.insertMovieCollect(data.asMovieCardResult()) }
        coVerify(exactly = 0) { movieRepository.deleteMovieCollect(any()) }
    }

    @Test
    @DisplayName("已收藏的電影切換收藏狀態時，應呼叫 deleteMovieCollect")
    fun `should delete movie collect when movie is already collected`() = runTest {
        // Given
        coEvery { movieRepository.deleteMovieCollect(any()) } returns Unit
        val viewModel = createViewModel(emptyList())
        val data = movieCardData(isCollect = true)

        // When
        viewModel.toggleMovieCollectStatus(data)
        advanceUntilIdle()

        // Then
        coVerify { movieRepository.deleteMovieCollect(data.asMovieCardResult()) }
        coVerify(exactly = 0) { movieRepository.insertMovieCollect(any()) }
    }

    @Test
    @DisplayName("呼叫 clearAllMovieHistory 應清空所有歷史紀錄")
    fun `clearAllMovieHistory should delete all history`() = runTest {
        // Given
        coEvery { movieRepository.deleteAllMovieHistory() } returns true
        val viewModel = createViewModel(emptyList())

        // When
        viewModel.clearAllMovieHistory()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { movieRepository.deleteAllMovieHistory() }
    }
}
