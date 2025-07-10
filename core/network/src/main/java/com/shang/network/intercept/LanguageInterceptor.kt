package com.shang.network.intercept

import com.shang.common.di.ApplicationScope
import com.shang.datastore.UserPreferenceDataSource
import com.shang.model.LanguageMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
import javax.inject.Inject

/**
 * 語言攔截器
 */
class LanguageInterceptor @Inject constructor(
    private val userPreferenceDataSource: UserPreferenceDataSource,
    @ApplicationScope private val scope: CoroutineScope,
) :
    Interceptor {

    @Volatile
    private var cachedLanguageCode: String = "zh-TW" // 預設值

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

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("language", cachedLanguageCode)
            .build()
        val newRequest = request.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }
}
