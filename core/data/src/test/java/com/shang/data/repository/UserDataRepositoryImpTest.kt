package com.shang.data.repository

import com.shang.datastore.UserPreferenceDataSource
import com.shang.model.ConfigurationBean
import com.shang.model.LanguageMode
import com.shang.model.ThemeMode
import com.shang.model.UserData
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

/**
 * UserDataRepositoryImp 的單元測試
 *
 * 驗證此 Repository 為 UserPreferenceDataSource 的單純轉發層。
 * userData 是建構時就綁定的 val，需先設定好 mock 的回傳值才能建立 repository 實例。
 */
@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("UserDataRepositoryImp Tests")
class UserDataRepositoryImpTest {

    private fun createRepository(
        userData: UserData = UserData.getDefault(),
    ): Pair<UserDataRepositoryImp, UserPreferenceDataSource> {
        val userPreferenceDataSource = mockk<UserPreferenceDataSource>(relaxUnitFun = true)
        every { userPreferenceDataSource.userData } returns flowOf(userData)
        return UserDataRepositoryImp(userPreferenceDataSource) to userPreferenceDataSource
    }

    @Test
    @DisplayName("userData 應直接轉發 DataSource 的資料流")
    fun `userData should delegate to data source`() = runTest {
        // Given
        val userData = UserData(ConfigurationBean(), ThemeMode.DARK, LanguageMode.ENGLISH)
        val (repository, _) = createRepository(userData)

        // When
        val result = repository.userData.first()

        // Then
        expectThat(result).isEqualTo(userData)
    }

    @Test
    @DisplayName("setConfiguration 應轉發呼叫給 DataSource")
    fun `setConfiguration should delegate to data source`() = runTest {
        // Given
        val (repository, userPreferenceDataSource) = createRepository()
        val config = ConfigurationBean(changeKeys = listOf("images"))

        // When
        repository.setConfiguration(config)

        // Then
        coVerify(exactly = 1) { userPreferenceDataSource.setConfiguration(config) }
    }

    @Test
    @DisplayName("setThemeMode 應轉發呼叫給 DataSource")
    fun `setThemeMode should delegate to data source`() = runTest {
        // Given
        val (repository, userPreferenceDataSource) = createRepository()

        // When
        repository.setThemeMode(ThemeMode.DARK)

        // Then
        coVerify(exactly = 1) { userPreferenceDataSource.setThemeMode(ThemeMode.DARK) }
    }

    @Test
    @DisplayName("setLanguageMode 應轉發呼叫給 DataSource")
    fun `setLanguageMode should delegate to data source`() = runTest {
        // Given
        val (repository, userPreferenceDataSource) = createRepository()

        // When
        repository.setLanguageMode(LanguageMode.ENGLISH)

        // Then
        coVerify(exactly = 1) { userPreferenceDataSource.setLanguageMode(LanguageMode.ENGLISH) }
    }
}
