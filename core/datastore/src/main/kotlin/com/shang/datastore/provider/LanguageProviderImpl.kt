package com.shang.datastore.provider

import com.shang.common.LanguageProvider
import com.shang.common.di.ApplicationScope
import com.shang.datastore.UserPreferenceDataSource
import com.shang.model.LanguageMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageProviderImpl @Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
    @ApplicationScope private val scope: CoroutineScope,
) : LanguageProvider {

    @Volatile
    private var cachedLanguageCode: String = "zh-TW" // 預設值

    override fun getLanguageCode(): String {
        return cachedLanguageCode
    }

    init {
        // 監聽語言變化並更新緩存
        scope.launch {
            userPreferenceDataSource.userData.collect { userData ->
                cachedLanguageCode = when (userData.languageMode) {
                    LanguageMode.ENGLISH -> "en-US"
                    LanguageMode.TRADITIONAL_CHINESE -> "zh-TW"
                    else -> Locale.getDefault().language
                }
            }
        }
    }
}
