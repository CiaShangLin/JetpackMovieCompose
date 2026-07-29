package com.shang.datastore.provider

import com.shang.datastore.UserPreferenceDataSource
import com.shang.model.ConfigurationBean
import com.shang.model.LanguageMode
import com.shang.model.ThemeMode
import com.shang.model.UserData
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

/**
 * BaseHostUrlProviderImpl 的單元測試
 *
 * 驗證依 DataStore 中的 baseUrl 更新，並確保結尾補上 "/"
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("BaseHostUrlProviderImpl Tests")
class BaseHostUrlProviderImplTest {

    private fun userDataWithBaseUrl(baseUrl: String) = UserData(
        configuration = ConfigurationBean(images = ConfigurationBean.Images(baseUrl = baseUrl)),
        themeMode = ThemeMode.SYSTEM,
        languageMode = LanguageMode.SYSTEM_DEFAULT,
    )

    @Test
    @DisplayName("baseUrl 不以 / 結尾時應自動補上")
    fun `getBaseHostUrl should append trailing slash when missing`() = runTest {
        // Given
        val userPreferenceDataSource = mockk<UserPreferenceDataSource>()
        every { userPreferenceDataSource.userData } returns
            flowOf(userDataWithBaseUrl("http://image.tmdb.org/t/p"))
        val provider = BaseHostUrlProviderImpl(userPreferenceDataSource, this)

        // When
        advanceUntilIdle()

        // Then
        expectThat(provider.getBaseHostUrl()).isEqualTo("http://image.tmdb.org/t/p/")
    }

    @Test
    @DisplayName("baseUrl 已以 / 結尾時應保持原樣")
    fun `getBaseHostUrl should keep url unchanged when already ending with slash`() = runTest {
        // Given
        val userPreferenceDataSource = mockk<UserPreferenceDataSource>()
        every { userPreferenceDataSource.userData } returns
            flowOf(userDataWithBaseUrl("http://image.tmdb.org/t/p/"))
        val provider = BaseHostUrlProviderImpl(userPreferenceDataSource, this)

        // When
        advanceUntilIdle()

        // Then
        expectThat(provider.getBaseHostUrl()).isEqualTo("http://image.tmdb.org/t/p/")
    }

    @Test
    @DisplayName("baseUrl 尚未取得前，getBaseHostUrl 應回傳初始空字串")
    fun `getBaseHostUrl should return empty string before data arrives`() {
        // Given
        val userPreferenceDataSource = mockk<UserPreferenceDataSource>()
        every { userPreferenceDataSource.userData } returns emptyFlow()
        val provider = BaseHostUrlProviderImpl(userPreferenceDataSource, CoroutineScope(Dispatchers.Unconfined))

        // When
        val result = provider.getBaseHostUrl()

        // Then
        expectThat(result).isEqualTo("")
    }
}
