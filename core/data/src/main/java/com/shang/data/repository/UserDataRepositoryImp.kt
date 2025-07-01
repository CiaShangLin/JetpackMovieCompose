package com.shang.data.repository

import com.shang.datastore.UserPreferenceDataSource
import com.shang.model.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImp @Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
) : UserDataRepository {
    override val userData: Flow<UserData> = userPreferenceDataSource.userData

    override suspend fun setShowCompleted(showCompleted: Boolean) {
        userPreferenceDataSource.setShowCompleted(showCompleted)
    }

    override suspend fun setVersion(version: Int) {
        userPreferenceDataSource.setVersion(version)
    }
}
