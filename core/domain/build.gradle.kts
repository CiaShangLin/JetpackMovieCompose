import deps.commonModule
import deps.dataModule
import deps.hilt
import deps.modelModule
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
    hilt()
    dataModule()
    commonModule()
    modelModule()
    testDeps()
    testImplDeps()
}
