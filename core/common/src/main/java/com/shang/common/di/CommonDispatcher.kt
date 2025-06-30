package com.shang.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val commonDispatcher: CommonDispatcher)

enum class CommonDispatcher() {
    Default,
    IO,
    Main,
}
