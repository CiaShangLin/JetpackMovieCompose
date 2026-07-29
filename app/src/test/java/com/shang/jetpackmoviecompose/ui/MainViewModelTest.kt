package com.shang.jetpackmoviecompose.ui

import com.shang.data.repository.UserDataRepository
import com.shang.domain.usecase.GetConfigurationUseCase
import com.shang.model.ConfigurationBean
import com.shang.model.LanguageMode
import com.shang.model.ThemeMode
import com.shang.model.UserData
import io.mockk.clearAllMocks
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

/**
 * MainViewModel 的單元測試
 *
 * 驗證 configuration 依 UseCase 回應轉換為 Success/Error 狀態、retryConfiguration 重新觸發請求，
 * 以及 userData 直接轉發 Repository 資料流
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("MainViewModel Tests")
class MainViewModelTest {

    private val getConfigurationUseCase = mockk<GetConfigurationUseCase>()
    private val userDataRepository = mockk<UserDataRepository>()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        clearAllMocks()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(userData: UserData = UserData.getDefault()): MainViewModel {
        every { userDataRepository.userData } returns flowOf(userData)
        return MainViewModel(getConfigurationUseCase, userDataRepository)
    }

    @Test
    @DisplayName("UseCase 回傳成功時 configuration 應轉換為 Success 狀態")
    fun `configuration should emit success state when use case succeeds`() = runTest {
        // Given
        val config = ConfigurationBean(changeKeys = listOf("images"))
        every { getConfigurationUseCase() } returns flowOf(Result.success(config))
        val viewModel = createViewModel()

        // When
        val job = launch { viewModel.configuration.collect {} }
        advanceUntilIdle()

        // Then
        expectThat(viewModel.configuration.value).isEqualTo(MainUiState.Success(config))
        job.cancel()
    }

    @Test
    @DisplayName("UseCase 回傳失敗時 configuration 應轉換為 Error 狀態")
    fun `configuration should emit error state when use case fails`() = runTest {
        // Given
        every { getConfigurationUseCase() } returns flowOf(Result.failure(RuntimeException("network error")))
        val viewModel = createViewModel()

        // When
        val job = launch { viewModel.configuration.collect {} }
        advanceUntilIdle()

        // Then
        expectThat(viewModel.configuration.value).isA<MainUiState.Error>()
        job.cancel()
    }

    @Test
    @DisplayName("呼叫 retryConfiguration 應重新觸發設定請求")
    fun `retryConfiguration should trigger configuration fetch again`() = runTest {
        // Given
        val config = ConfigurationBean(changeKeys = listOf("images"))
        every { getConfigurationUseCase() } returns flowOf(Result.success(config))
        val viewModel = createViewModel()
        val job = launch { viewModel.configuration.collect {} }
        advanceUntilIdle()

        // When
        viewModel.retryConfiguration()
        advanceUntilIdle()

        // Then（初始 onStart 觸發一次 + retryConfiguration 觸發一次）
        verify(exactly = 2) { getConfigurationUseCase() }
        job.cancel()
    }

    @Test
    @DisplayName("userData 應直接轉發 Repository 的資料流")
    fun `userData should reflect repository data`() = runTest {
        // Given
        val userData = UserData(ConfigurationBean(), ThemeMode.DARK, LanguageMode.ENGLISH)
        val viewModel = createViewModel(userData)

        // When
        val job = launch { viewModel.userData.collect {} }
        advanceUntilIdle()

        // Then
        expectThat(viewModel.userData.value).isEqualTo(userData)
        job.cancel()
    }
}
