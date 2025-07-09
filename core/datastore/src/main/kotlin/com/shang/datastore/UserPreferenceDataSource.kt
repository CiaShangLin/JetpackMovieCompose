package com.shang.datastore

import androidx.datastore.core.DataStore
import com.shang.datastore.mapper.toModel
import com.shang.datastore.mapper.toProto
import com.shang.model.ConfigurationBean
import com.shang.model.ThemeMode
import com.shang.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {

    val userData = userPreferences.data.map {
        UserData(
            configuration = it.configuration.toModel(),
            themeMode = when (it.theme) {
                ThemeProto.THEME_LIGHT -> ThemeMode.LIGHT
                ThemeProto.THEME_DARK -> ThemeMode.DARK
                else -> ThemeMode.SYSTEM
            },
        )
    }

    suspend fun setConfiguration(configurationBean: ConfigurationBean) {
        userPreferences.updateData {
            it.copy {
                this.configuration = configurationBean.toProto()
            }
        }
    }

    suspend fun setThemeMode(themeMode: ThemeMode) {
        userPreferences.updateData {
            it.copy {
                theme = when (themeMode) {
                    ThemeMode.LIGHT -> ThemeProto.THEME_LIGHT
                    ThemeMode.DARK -> ThemeProto.THEME_DARK
                    else -> ThemeProto.THEME_SYSTEM_DEFAULT
                }
            }
        }
    }
}
