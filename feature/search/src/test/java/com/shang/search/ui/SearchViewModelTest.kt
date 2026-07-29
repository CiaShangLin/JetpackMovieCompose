package com.shang.search.ui

import com.shang.data.repository.MovieRepository
import com.shang.domain.usecase.GetMovieSearchUseCase
import com.shang.ui.MovieCardData
import com.shang.ui.asMovieCardResult
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

/**
 * SearchViewModel 的單元測試
 *
 * 驗證 toggleMovieCollectStatus 的加入／移除收藏兩種情境
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("SearchViewModel Tests")
class SearchViewModelTest {

    private val movieRepository = mockk<MovieRepository>(relaxed = true)
    private val getMovieSearchUseCase = mockk<GetMovieSearchUseCase>()
    private lateinit var viewModel: SearchViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        clearAllMocks()
        viewModel = SearchViewModel(movieRepository, getMovieSearchUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
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
    @DisplayName("尚未收藏的電影切換收藏狀態時，應呼叫 insertMovieCollect")
    fun `should insert movie collect when movie is not collected yet`() {
        // Given
        val data = movieCardData(isCollect = false)

        // When
        viewModel.toggleMovieCollectStatus(data)

        // Then
        coVerify(timeout = 2000) { movieRepository.insertMovieCollect(data.asMovieCardResult()) }
        coVerify(exactly = 0) { movieRepository.deleteMovieCollect(any()) }
    }

    @Test
    @DisplayName("已收藏的電影切換收藏狀態時，應呼叫 deleteMovieCollect")
    fun `should delete movie collect when movie is already collected`() {
        // Given
        val data = movieCardData(isCollect = true)

        // When
        viewModel.toggleMovieCollectStatus(data)

        // Then
        coVerify(timeout = 2000) { movieRepository.deleteMovieCollect(data.asMovieCardResult()) }
        coVerify(exactly = 0) { movieRepository.insertMovieCollect(any()) }
    }
}
