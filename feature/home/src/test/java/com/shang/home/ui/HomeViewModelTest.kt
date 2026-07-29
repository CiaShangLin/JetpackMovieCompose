package com.shang.home.ui

import com.shang.data.repository.MovieRepository
import com.shang.data.repository.UserDataRepository
import com.shang.model.MovieGenreBean
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
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

/**
 * HomeViewModel 的單元測試
 *
 * 驗證 movieGenres 依 Repository 回應轉換為 Loading/Success/Error 狀態，以及 retry() 重新觸發請求
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("HomeViewModel Tests")
class HomeViewModelTest {

    private val userDataRepository = mockk<UserDataRepository>(relaxed = true)
    private val movieRepository = mockk<MovieRepository>()
    private lateinit var viewModel: HomeViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        clearAllMocks()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    @DisplayName("Repository 回傳成功時應轉換為 Success 狀態")
    fun `movieGenres should emit success state when repository succeeds`() = runTest {
        // Given
        val genres = MovieGenreBean(genres = listOf(MovieGenreBean.MovieGenre(id = 1, name = "Action")))
        every { movieRepository.getMovieGenres() } returns flowOf(Result.success(genres))
        viewModel = HomeViewModel(userDataRepository, movieRepository)

        // When
        val job = launch { viewModel.movieGenres.collect {} }
        advanceUntilIdle()

        // Then
        expectThat(viewModel.movieGenres.value).isEqualTo(HomeUiState.Success(genres))
        job.cancel()
    }

    @Test
    @DisplayName("Repository 回傳失敗時應轉換為 Error 狀態")
    fun `movieGenres should emit error state when repository fails`() = runTest {
        // Given
        every { movieRepository.getMovieGenres() } returns flowOf(Result.failure(RuntimeException("network error")))
        viewModel = HomeViewModel(userDataRepository, movieRepository)

        // When
        val job = launch { viewModel.movieGenres.collect {} }
        advanceUntilIdle()

        // Then
        expectThat(viewModel.movieGenres.value).isA<HomeUiState.Error>()
        job.cancel()
    }

    @Test
    @DisplayName("呼叫 retry 應重新觸發 Repository 請求")
    fun `retry should trigger repository call again`() = runTest {
        // Given
        val genres = MovieGenreBean(genres = listOf(MovieGenreBean.MovieGenre(id = 1, name = "Action")))
        every { movieRepository.getMovieGenres() } returns flowOf(Result.success(genres))
        viewModel = HomeViewModel(userDataRepository, movieRepository)
        val job = launch { viewModel.movieGenres.collect {} }
        advanceUntilIdle()

        // When
        viewModel.retry()
        advanceUntilIdle()

        // Then
        verify(exactly = 2) { movieRepository.getMovieGenres() }
        job.cancel()
    }
}
