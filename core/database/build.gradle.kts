import deps.commonModule
import deps.hilt
import deps.modelModule
import deps.room
import deps.testDeps
import deps.testImplDeps
import plugs.SharedLibraryGradlePlugin

plugins {
    id(plugs.BuildPlugins.ANDROID_LIBRARY)
}

apply<SharedLibraryGradlePlugin>()

android {
    namespace = "com.shang.database"
}

dependencies {

    modelModule()
    commonModule()
    room()
    hilt()
    testDeps()
    testImplDeps()
}
