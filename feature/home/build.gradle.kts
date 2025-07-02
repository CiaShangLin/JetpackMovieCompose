import deps.androidx
import deps.commonModule
import deps.dataModule
import deps.domainModule
import deps.hilt
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
    namespace = "com.shang.home"
}

dependencies {
    androidx()
    hilt()
    modelModule()
    commonModule()
    dataModule()
    domainModule()
    testDeps()
    testImplDeps()
}
