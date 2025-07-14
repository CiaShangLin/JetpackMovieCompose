package com.shang.network.intercept

import okhttp3.Interceptor
import okhttp3.Response

/**
 * OkHttp 攔截器：自動為所有請求加上 api_key 查詢參數
 * @param apiKey API 金鑰，透過建構子注入
 */
class ApiKeyInterceptor(private val apiKey: String) : Interceptor {

    companion object {
        const val API_KEY_PARAM = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter(API_KEY_PARAM, apiKey)
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}
