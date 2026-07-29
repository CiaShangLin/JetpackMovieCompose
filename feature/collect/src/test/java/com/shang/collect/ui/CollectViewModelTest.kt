package com.shang.collect.ui

import com.shang.data.repository.MovieRepository
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
import strikt.assertions.isEqualTo

/**
 * CollectViewModel 的單元測試
 *
 * 驗證 allMovieCollect 依收藏清單轉換為 Empty/Success 狀態，以及 toggleMovieCollectStatus 加入移除收藏
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("CollectViewModel Tests")
class CollectViewModelTest {

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

    private fun createViewModel(collectList: List<MovieCardResult>): CollectViewModel {
        every { movieRepository.getAllMovieCollect() } returns flowOf(collectList)
        return CollectViewModel(movieRepository)
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
    @DisplayName("有收藏清單時應轉換為 Success 狀態")
    fun `allMovieCollect should emit success state when collect list is not empty`() = runTest {
        // Given
        val collectList = listOf(MovieCardResult(id = 1, title = "movie-1", isCollect = true))
        val viewModel = createViewModel(collectList)

        // When
        val job = launch { viewModel.allMovieCollect.collect {} }
        advanceUntilIdle()

        // Then
        expectThat(viewModel.allMovieCollect.value).isEqualTo(CollectUiState.Success(collectList))
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
}
