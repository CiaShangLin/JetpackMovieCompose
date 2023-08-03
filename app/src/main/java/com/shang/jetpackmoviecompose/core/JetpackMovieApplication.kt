package com.shang.jetpackmoviecompose.core

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDexApplication
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.intercept.Interceptor
import coil.request.ImageResult
import com.shang.jetpackmoviecompose.globalData.Configuration
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class JetpackMovieApplication : MultiDexApplication(), ImageLoaderFactory {


    companion object {
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        context = this
        MMKV.initialize(this)
//        AppCompatDelegate.setDefaultNightMode(UserSetting.theme)

    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .components {
                add(BaseUrlInterceptor())
            }
            .build()
    }
}

/**
 * Coil的攔截器
 * Movie Api的海報只會給/xxxx.jpg
 * 所以需要自己加上Host
 */
class BaseUrlInterceptor : Interceptor {
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        var url = chain.request.data as String
        if (url.startsWith("/")) {
            url = "${Configuration.getConfiguration()?.images?.base_url}original${url}"
        }
        val value = chain.request
            .newBuilder()
            .data(url)
            .build()

        return chain.proceed(value)
    }
}