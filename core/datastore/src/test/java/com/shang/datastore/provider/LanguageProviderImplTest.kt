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
 * LanguageProviderImpl 的單元測試
 *
 * 驗證依 DataStore 中的語言設定更新對應的語言代碼
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("LanguageProviderImpl Tests")
class LanguageProviderImplTest {

    private fun userDataWithLanguage(languageMode: LanguageMode) = UserData(
        configuration = ConfigurationBean(),
        themeMode = ThemeMode.SYSTEM,
        languageMode = languageMode,
    )

    @Test
    @DisplayName("語言模式為英文時應回傳 en-US")
    fun `getLanguageCode should return en-US when language mode is english`() = runTest {
        // Given
        val userPreferenceDataSource = mockk<UserPreferenceDataSource>()
        every { userPreferenceDataSource.userData } returns
            flowOf(userDataWithLanguage(LanguageMode.ENGLISH))
        val provider = LanguageProviderImpl(userPreferenceDataSource, this)

        // When
        advanceUntilIdle()

        // Then
        expectThat(provider.getLanguageCode()).isEqualTo("en-US")
    }

    @Test
    @DisplayName("語言模式為繁體中文時應回傳 zh-TW")
    fun `getLanguageCode should return zh-TW when language mode is traditional chinese`() = runTest {
        // Given
        val userPreferenceDataSource = mockk<UserPreferenceDataSource>()
        every { userPreferenceDataSource.userData } returns
            flowOf(userDataWithLanguage(LanguageMode.TRADITIONAL_CHINESE))
        val provider = LanguageProviderImpl(userPreferenceDataSource, this)

        // When
        advanceUntilIdle()

        // Then
        expectThat(provider.getLanguageCode()).isEqualTo("zh-TW")
    }

    @Test
    @DisplayName("尚未取得資料前，getLanguageCode 應回傳預設值 zh-TW")
    fun `getLanguageCode should return default value before data arrives`() {
        // Given
        val userPreferenceDataSource = mockk<UserPreferenceDataSource>()
        every { userPreferenceDataSource.userData } returns emptyFlow()
        val provider = LanguageProviderImpl(userPreferenceDataSource, CoroutineScope(Dispatchers.Unconfined))

        // When
        val result = provider.getLanguageCode()

        // Then
        expectThat(result).isEqualTo("zh-TW")
    }
}
