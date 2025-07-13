package com.shang.network.intercept

import com.shang.common.LanguageProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * 語言攔截器
 */
class LanguageInterceptor @Inject constructor(
    private val languageCodeProvider: LanguageProvider,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("language", languageCodeProvider.getLanguageCode())
            .build()
        val newRequest = request.newBuilder().url(url).build()
        return chain.proceed(newRequest)
    }
}
