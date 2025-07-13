import deps.androidx
import deps.commonModule
import deps.designsystemModule
import deps.hilt
import deps.lottie
import deps.modelModule
import deps.testDeps
import deps.testImplDeps
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
    id(plugs.BuildPlugins.KOTLIN_COMPOSE) version deps.DependenciesVersions.KOTLIN_COMPILER
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.shang.ui"
}

dependencies {
    androidx()
    hilt()
    lottie()

    modelModule()
    designsystemModule()
    commonModule()
    testDeps()
    testImplDeps()
}
