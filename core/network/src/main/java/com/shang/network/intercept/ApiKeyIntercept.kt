package com.shang.network.intercept

import okhttp3.Interceptor
import okhttp3.Response

/**
 * OkHttp 攔截器：自動為所有請求加上 api_key 查詢參數
 */
class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .addQueryParameter("api_key", "90b44d4486f4ba2b48dbc22e2099b38b")
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}