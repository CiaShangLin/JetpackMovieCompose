package com.shang.datastore.provider

import com.shang.common.BaseHostUrlProvider
import com.shang.common.di.ApplicationScope
import com.shang.datastore.UserPreferenceDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseHostUrlProviderImpl @Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
    @ApplicationScope private val applicationScope: CoroutineScope,
) : BaseHostUrlProvider {

    @Volatile
    private var baseUrl: String = ""

    override fun getBaseHostUrl(): String = baseUrl

    init {
        applicationScope.launch {
            userPreferenceDataSource.userData.map {
                it.configuration.images.baseUrl
            }.distinctUntilChanged().collect { url ->
                baseUrl = url.let {
                    // 確保 baseUrl 以 / 結尾
                    if (it.isNotEmpty() && !it.endsWith("/")) "$it/" else it
                }
            }
        }
    }
}
