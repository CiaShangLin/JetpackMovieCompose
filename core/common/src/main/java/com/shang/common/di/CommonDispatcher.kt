package com.shang.common.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val commonDispatcher: CommonDispatcher)

enum class CommonDispatcher() {
    Default,
    IO,
    Main,
}
