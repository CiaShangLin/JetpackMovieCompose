package com.shang.datastore.di

import com.shang.common.BaseHostUrlProvider
import com.shang.common.LanguageProvider
import com.shang.datastore.provider.BaseHostUrlProviderImpl
import com.shang.datastore.provider.LanguageProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserDataModule {

    @Binds
    @Singleton
    abstract fun bindLanguageProvider(
        impl: LanguageProviderImpl,
    ): LanguageProvider

    @Binds
    @Singleton
    abstract fun bindBaseHostUrlProvider(
        impl: BaseHostUrlProviderImpl,
    ): BaseHostUrlProvider
}
