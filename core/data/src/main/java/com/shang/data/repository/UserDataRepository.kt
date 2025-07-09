package com.shang.data.repository

import com.shang.model.ConfigurationBean
import com.shang.model.ThemeMode
import com.shang.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userData: Flow<UserData>

    suspend fun setConfiguration(configuration: ConfigurationBean)

    suspend fun setThemeMode(themeMode: ThemeMode)
}
