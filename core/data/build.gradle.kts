import deps.coroutine
import deps.modelModule
import deps.networkModule
import deps.testDeps
import deps.testImplDeps
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
//    id(plugs.BuildPlugins.KOTLIN_COMPOSE) version "2.1.21"
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.shang.data"
}

dependencies {

    coroutine()
    networkModule()
//    databaseModule()
//    datastoreModule()
    modelModule()
    testDeps()
    testImplDeps()
}
