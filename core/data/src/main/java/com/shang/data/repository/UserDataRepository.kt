package com.shang.data.repository

import com.shang.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {

    val userData: Flow<UserData>

    suspend fun setShowCompleted(showCompleted: Boolean)

    suspend fun setVersion(version: Int)
}
