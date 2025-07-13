import deps.chucker
import deps.commonModule
import deps.hilt
import deps.kotlinx
import deps.modelModule
import deps.okHttp
import deps.retrofit
import deps.testDeps
import deps.testImplDeps
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
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
    modelModule()
    commonModule()
    testDeps()
    testImplDeps()
}
