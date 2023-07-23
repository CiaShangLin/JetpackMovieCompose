package com.shang.jetpackmoviecompose.api


import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class LanguageInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder().addQueryParameter("language", getLanguage()).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

    private fun getLanguage() = when (Locale.getDefault().language) {
        Locale.TAIWAN.language -> "zh-TW"
        Locale.CHINA.language -> "zh"
        Locale.ENGLISH.language -> "en-US"
        else -> "zh-TW"
    }
}