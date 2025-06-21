import deps.testDeps
import deps.testImplDeps
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
    id(plugs.BuildPlugins.KOTLIN_COMPOSE) version "2.1.21"
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.shang.model"
}

dependencies {
    testDeps()
    testImplDeps()
}