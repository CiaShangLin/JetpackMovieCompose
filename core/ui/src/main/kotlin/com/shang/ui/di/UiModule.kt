package com.shang.ui.di

import android.content.Context
import coil3.ImageLoader
import coil3.request.crossfade
import com.shang.ui.coil.HostInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UiModule {

    @Provides
    @Singleton
    fun provideImageLoaderFactory(
        @ApplicationContext context: Context,
        hostInterceptor: HostInterceptor,
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(hostInterceptor)
            }
            .crossfade(true)
            .build()
    }
}
