import deps.coroutine
import deps.hilt
import deps.testDeps
import deps.testImplDeps
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.shang.common"
}

dependencies {
    coroutine()
    hilt()
    testDeps()
    testImplDeps()
}
