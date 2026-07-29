package com.shang.moviedetail.ui

import com.shang.common.UiState
import com.shang.data.repository.MovieRepository
import com.shang.domain.usecase.GetMovieDetailUseCase
import com.shang.domain.usecase.GetMovieRecommendUseCase
import com.shang.model.MovieCardResult
import com.shang.model.MovieCastAndCrewBean
import com.shang.model.MovieDetailBean
import com.shang.model.MovieRecommendBean
import com.shang.ui.MovieCardData
import com.shang.ui.asMovieCardResult
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isTrue

/**
 * MovieDetailViewModel 的單元測試
 *
 * 驗證 movieDetail/movieCollect 狀態轉換、toggleCollect 加入移除收藏、retryMovieDetailApi 重新觸發請求
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("MovieDetailViewModel Tests")
class MovieDetailViewModelTest {

    private val movieRepository = mockk<MovieRepository>()
    private val movieId = 1

    @BeforeEach
    fun setUp() {
        // 使用 UnconfinedTestDispatcher：retryMovieDetailApi() 內部經 Dispatchers.IO 觸發，
        // 需要 Main 端能立即（非等待虛擬時間推進）處理來自其他 dispatcher 的 StateFlow 更新
        Dispatchers.setMain(UnconfinedTestDispatcher())
        clearAllMocks()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(
        collectedMovie: MovieCardResult? = null,
    ): MovieDetailViewModel {
        every { movieRepository.getMovieCollectEntityById(movieId) } returns
            flowOf(collectedMovie)
        every { movieRepository.getCollectedMovieIds() } returns flowOf(emptyList())
        every { movieRepository.getMovieRecommendations(movieId) } returns
            flowOf(Result.success(MovieRecommendBean()))
        every { movieRepository.getMovieActor(movieId) } returns
            flowOf(Result.success(MovieCastAndCrewBean(id = movieId)))
        val getMovieDetailUseCase = GetMovieDetailUseCase(movieRepository, Dispatchers.Unconfined)
        val getMovieRecommendUseCase = GetMovieRecommendUseCase(movieRepository, Dispatchers.Unconfined)
        return MovieDetailViewModel(movieRepository, getMovieDetailUseCase, getMovieRecommendUseCase, movieId)
    }

    private fun movieCardData() = MovieCardData(
        movieCardId = movieId,
        movieCardTitle = "title",
        movieCardPosterPath = "poster",
        movieCardReleaseDate = "2024-01-01",
        movieCardVoteAverage = 8.0,
        movieCardIsCollect = false,
        movieCardTimestamp = 0L,
    )

    @Test
    @DisplayName("電影詳情取得成功時應轉換為 Success 狀態")
    fun `movieDetail should emit success state when fetch succeeds`() = runTest {
        // Given
        val detail = MovieDetailBean(id = movieId, title = "movie-1")
        every { movieRepository.getMovieDetail(movieId) } returns flowOf(Result.success(detail))
        coEvery { movieRepository.insertMovieHistory(any()) } returns Unit
        val viewModel = createViewModel()

        // When
        val job = launch { viewModel.movieDetail.collect {} }
        advanceUntilIdle()

        // Then
        expectThat(viewModel.movieDetail.value).isEqualTo(MovieDetailUiState.Success(detail))
        job.cancel()
    }

    @Test
    @DisplayName("電影詳情取得失敗時應轉換為 Error 狀態")
    fun `movieDetail should emit error state when fetch fails`() = runTest {
        // Given
        every { movieRepository.getMovieDetail(movieId) } returns
            flowOf(Result.failure(RuntimeException("network error")))
        val viewModel = createViewModel()

        // When
        val job = launch { viewModel.movieDetail.collect {} }
        advanceUntilIdle()

        // Then
        expectThat(viewModel.movieDetail.value).isA<MovieDetailUiState.Error>()
        job.cancel()
    }

    @Test
    @DisplayName("呼叫 retryMovieDetailApi 應重新觸發電影詳情請求")
    fun `retryMovieDetailApi should trigger detail fetch again`() = runTest {
        // Given
        val detail = MovieDetailBean(id = movieId, title = "movie-1")
        every { movieRepository.getMovieDetail(movieId) } returns flowOf(Result.success(detail))
        coEvery { movieRepository.insertMovieHistory(any()) } returns Unit
        val viewModel = createViewModel()
        val job = launch { viewModel.movieDetail.collect {} }
        advanceUntilIdle()

        // When
        viewModel.retryMovieDetailApi()
        advanceUntilIdle()

        // Then
        verify(exactly = 2) { movieRepository.getMovieDetail(movieId) }
        job.cancel()
    }

    @Test
    @DisplayName("查無收藏紀錄時 movieCollect 應為 false")
    fun `movieCollect should be false when no collect entity found`() = runTest {
        // Given
        val viewModel = createViewModel(collectedMovie = null)

        // When
        val job = launch { viewModel.movieCollect.collect {} }
        advanceUntilIdle()

        // Then
        expectThat(viewModel.movieCollect.value).isEqualTo(false)
        job.cancel()
    }

    @Test
    @DisplayName("已收藏時 movieCollect 應為 true")
    fun `movieCollect should be true when collect entity exists`() = runTest {
        // Given
        val collectedMovie = MovieCardResult(id = movieId, title = "movie-1", isCollect = true)
        val viewModel = createViewModel(collectedMovie = collectedMovie)

        // When
        val job = launch { viewModel.movieCollect.collect {} }
        advanceUntilIdle()

        // Then
        expectThat(viewModel.movieCollect.value).isTrue()
        job.cancel()
    }

    @Test
    @DisplayName("演員資料取得成功時 movieActors 應轉換為 Success 狀態")
    fun `movieActors should emit success state`() = runTest {
        // Given
        val viewModel = createViewModel()

        // When
        val job = launch { viewModel.movieActors.collect {} }
        advanceUntilIdle()

        // Then
        expectThat(viewModel.movieActors.value).isA<UiState.Success<*>>()
        job.cancel()
    }

    @Test
    @DisplayName("尚未收藏時呼叫 toggleCollect 應呼叫 insertMovieCollect")
    fun `toggleCollect should insert movie collect when not collected`() = runTest {
        // Given
        coEvery { movieRepository.insertMovieCollect(any()) } returns Unit
        val viewModel = createViewModel()
        val data = movieCardData()

        // When
        viewModel.toggleCollect(data, isCollect = false)
        advanceUntilIdle()

        // Then
        coVerify(timeout = 2000) { movieRepository.insertMovieCollect(data.asMovieCardResult()) }
    }

    @Test
    @DisplayName("已收藏時呼叫 toggleCollect 應呼叫 deleteMovieCollect")
    fun `toggleCollect should delete movie collect when already collected`() = runTest {
        // Given
        coEvery { movieRepository.deleteMovieCollect(any()) } returns Unit
        val viewModel = createViewModel()
        val data = movieCardData()

        // When
        viewModel.toggleCollect(data, isCollect = true)
        advanceUntilIdle()

        // Then
        coVerify(timeout = 2000) { movieRepository.deleteMovieCollect(data.asMovieCardResult()) }
    }
}
