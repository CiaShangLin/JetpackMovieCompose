import deps.commonModule
import deps.coroutine
import deps.databaseModule
import deps.hilt
import deps.modelModule
import deps.networkModule
import deps.testDeps
import deps.testImplDeps
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.shang.data"
}

dependencies {

    coroutine()
    networkModule()
    hilt()
    databaseModule()
//    datastoreModule()
    modelModule()
    commonModule()
    testDeps()
    testImplDeps()
}
