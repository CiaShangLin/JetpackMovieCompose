package com.shang.setting.ui

import com.shang.data.repository.UserDataRepository
import com.shang.model.ConfigurationBean
import com.shang.model.LanguageMode
import com.shang.model.ThemeMode
import com.shang.model.UserData
import io.mockk.clearAllMocks
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
 * SettingViewModel 的單元測試
 *
 * 驗證 userData 直接轉發 Repository 資料流，以及 setThemeMode/setLanguageMode 呼叫轉發
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("SettingViewModel Tests")
class SettingViewModelTest {

    private val userDataRepository = mockk<UserDataRepository>(relaxUnitFun = true)

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        clearAllMocks()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(userData: UserData = UserData.getDefault()): SettingViewModel {
        every { userDataRepository.userData } returns flowOf(userData)
        return SettingViewModel(userDataRepository)
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

    @Test
    @DisplayName("呼叫 setThemeMode 應轉發至 Repository")
    fun `setThemeMode should delegate to repository`() = runTest {
        // Given
        val viewModel = createViewModel()

        // When
        viewModel.setThemeMode(ThemeMode.DARK)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { userDataRepository.setThemeMode(ThemeMode.DARK) }
    }

    @Test
    @DisplayName("呼叫 setLanguageMode 應轉發至 Repository")
    fun `setLanguageMode should delegate to repository`() = runTest {
        // Given
        val viewModel = createViewModel()

        // When
        viewModel.setLanguageMode(LanguageMode.ENGLISH)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { userDataRepository.setLanguageMode(LanguageMode.ENGLISH) }
    }
}
