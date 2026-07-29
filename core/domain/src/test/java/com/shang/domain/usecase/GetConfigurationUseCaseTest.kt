package com.shang.domain.usecase

import com.shang.data.repository.MovieRepository
import com.shang.data.repository.UserDataRepository
import com.shang.model.ConfigurationBean
import com.shang.model.LanguageMode
import com.shang.model.ThemeMode
import com.shang.model.UserData
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
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
 * GetConfigurationUseCase 的單元測試
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("GetConfigurationUseCase Tests")
class GetConfigurationUseCaseTest {

    private val movieRepository = mockk<MovieRepository>()
    private val userDataRepository = mockk<UserDataRepository>(relaxUnitFun = true)
    private lateinit var useCase: GetConfigurationUseCase

    @BeforeEach
    fun setUp() {
        useCase = GetConfigurationUseCase(movieRepository, userDataRepository, Dispatchers.Unconfined)
    }

    @Test
    @DisplayName("API 成功時應寫入本地快取並回傳成功結果")
    fun `should cache configuration and emit success when api succeeds`() = runTest {
        // Given
        val config = ConfigurationBean(changeKeys = listOf("images"))
        every { movieRepository.getConfiguration() } returns flowOf(Result.success(config))

        // When
        val result = useCase().first()

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(result.getOrNull()).isEqualTo(config)
        coVerify { userDataRepository.setConfiguration(config) }
    }

    @Test
    @DisplayName("API 失敗但本地有快取資料時應回傳快取資料")
    fun `should emit cached configuration when api fails but cache exists`() = runTest {
        // Given
        val cachedConfig = ConfigurationBean(changeKeys = listOf("cached"))
        val error = RuntimeException("network error")
        every { movieRepository.getConfiguration() } returns flowOf(Result.failure(error))
        every { userDataRepository.userData } returns flowOf(
            UserData(cachedConfig, ThemeMode.SYSTEM, LanguageMode.SYSTEM_DEFAULT),
        )

        // When
        val result = useCase().first()

        // Then
        expectThat(result.isSuccess).isTrue()
        expectThat(result.getOrNull()).isEqualTo(cachedConfig)
    }

    @Test
    @DisplayName("API 失敗且本地無快取資料時應回傳原本的錯誤")
    fun `should emit original failure when api fails and no cache exists`() = runTest {
        // Given
        val error = RuntimeException("network error")
        every { movieRepository.getConfiguration() } returns flowOf(Result.failure(error))
        every { userDataRepository.userData } returns emptyFlow()

        // When
        val result = useCase().first()

        // Then
        expectThat(result.isFailure).isTrue()
        expectThat(result.exceptionOrNull()).isEqualTo(error)
    }
}
