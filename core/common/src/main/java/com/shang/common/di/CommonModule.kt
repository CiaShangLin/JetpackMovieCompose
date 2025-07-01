package com.shang.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Dispatcher(CommonDispatcher.Default)
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Dispatcher(CommonDispatcher.IO)
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Dispatcher(CommonDispatcher.Main)
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @Provides
    @Singleton
    @ApplicationScope
    fun providesCoroutineScope(
        @Dispatcher(CommonDispatcher.Default) dispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}
