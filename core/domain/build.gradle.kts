import deps.commonModule
import deps.dataModule
import deps.hilt
import deps.modelModule
import deps.paging3
import deps.testDeps
import deps.testImplDeps
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.shang.domain"
}

dependencies {
    paging3()
    hilt()
    dataModule()
    commonModule()
    modelModule()
    testDeps()
    testImplDeps()
}
