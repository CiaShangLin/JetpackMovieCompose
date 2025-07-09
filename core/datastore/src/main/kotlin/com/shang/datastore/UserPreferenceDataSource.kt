package com.shang.datastore

import androidx.datastore.core.DataStore
import com.shang.datastore.mapper.toModel
import com.shang.datastore.mapper.toProto
import com.shang.model.ConfigurationBean
import com.shang.model.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {

    val userData = userPreferences.data.map {
        UserData(
            configuration = it.configuration.toModel(),
        )
    }

    suspend fun setConfiguration(configurationBean: ConfigurationBean) {
        userPreferences.updateData {
            it.copy {
                this.configuration = configurationBean.toProto()
            }
        }
    }
}
