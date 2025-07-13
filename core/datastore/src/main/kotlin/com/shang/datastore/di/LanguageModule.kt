package com.shang.datastore.di

import com.shang.common.LanguageProvider
import com.shang.datastore.LanguageCodeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LanguageModule {

    @Binds
    @Singleton
    abstract fun bindLanguageProvider(
        impl: LanguageCodeProvider,
    ): LanguageProvider
}
