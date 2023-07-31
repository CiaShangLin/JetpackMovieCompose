package com.shang.jetpackmoviecompose.di

import com.shang.jetpackmoviecompose.api.ConfigurationApi
import com.shang.jetpackmoviecompose.ui.splash.SplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SplashModule {

    @Singleton
    @Provides
    fun provideSplashRepository(configurationApi: ConfigurationApi) =SplashRepository(configurationApi)
}
