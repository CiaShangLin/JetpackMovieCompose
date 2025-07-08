import deps.androidx
import deps.dataModule
import deps.designsystemModule
import deps.domainModule
import deps.hilt
import deps.kotlinx
import deps.modelModule
import deps.testDeps
import deps.testImplDeps
import deps.uiModule
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
    id(plugs.BuildPlugins.KOTLIN_COMPOSE) version deps.DependenciesVersions.KOTLIN_COMPILER
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.shang.history"
}

dependencies {
    androidx()
    hilt()
    kotlinx()

    designsystemModule()
    uiModule()
    dataModule()
    domainModule()
    modelModule()
    testDeps()
    testImplDeps()
}
