package com.shang.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Dispatcher(CommonDispatcher.Default)
    @Provides
    @Singleton
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Dispatcher(CommonDispatcher.IO)
    @Provides
    @Singleton
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Dispatcher(CommonDispatcher.Main)
    @Provides
    @Singleton
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}
