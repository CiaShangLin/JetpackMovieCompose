package com.shang.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore

val Context.userPreferencesDataStore: DataStore<UserPreferences> by dataStore(
    fileName = "user_preferences.pb",
    serializer = UserPreferencesSerializer,
)
