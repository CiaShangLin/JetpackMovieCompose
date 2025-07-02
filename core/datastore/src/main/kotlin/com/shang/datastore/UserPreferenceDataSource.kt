package com.shang.datastore

import androidx.datastore.core.DataStore
import com.shang.model.ConfigurationBean
import com.shang.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {

    val userData = userPreferences.data.map {
        UserData(
            showCompleted = it.showCompleted,
            version = it.version,
        )
    }

    suspend fun setShowCompleted(showCompleted: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.showCompleted = showCompleted
            }
        }
    }

    suspend fun setVersion(version: Int) {
        userPreferences.updateData {
            it.copy {
                this.version = version
            }
        }
    }

    suspend fun setConfiguration(configurationBean: ConfigurationBean) {
        userPreferences.updateData {
            it.toBuilder()
                .setConfiguration(configurationBean.toProto())
                .build()
        }
    }
}
