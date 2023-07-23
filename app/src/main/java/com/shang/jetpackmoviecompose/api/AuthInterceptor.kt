package com.shang.jetpackmoviecompose.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    private val api_key = "90b44d4486f4ba2b48dbc22e2099b38b"

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder().addQueryParameter("api_key", api_key).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}