import deps.commonModule
import deps.coroutine
import deps.databaseModule
import deps.datastoreModule
import deps.hilt
import deps.modelModule
import deps.networkModule
import deps.paging3
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
    paging3()
    coroutine()
    networkModule()
    hilt()
    databaseModule()
    datastoreModule()
    modelModule()
    commonModule()
    testDeps()
    testImplDeps()
}
