import deps.chucker
import deps.hilt
import deps.kotlinx
import deps.okHttp
import deps.retrofit
import deps.testDeps
import deps.testImplDeps
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
    id(plugs.BuildPlugins.KOTLIN_COMPOSE) version "2.1.21"
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.shang.network"
}

dependencies {
    chucker()
    retrofit()
    okHttp()
    kotlinx()
    hilt()
    testDeps()
    testImplDeps()
}