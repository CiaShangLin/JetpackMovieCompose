package com.shang.data.repository

import com.shang.datastore.UserPreferenceDataSource
import com.shang.model.ConfigurationBean
import com.shang.model.ThemeMode
import com.shang.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImp @Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
) : UserDataRepository {
    override val userData: Flow<UserData> = userPreferenceDataSource.userData

    override suspend fun setConfiguration(configuration: ConfigurationBean) {
        userPreferenceDataSource.setConfiguration(configuration)
    }

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        userPreferenceDataSource.setThemeMode(themeMode)
    }
}
